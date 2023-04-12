package com.pacoprojects.service;

import com.pacoprojects.dto.ProdutoDto;
import com.pacoprojects.dto.projections.ProdutoProjections;
import com.pacoprojects.email.EmailMessage;
import com.pacoprojects.email.EmailObject;
import com.pacoprojects.email.EmailService;
import com.pacoprojects.mapper.ProdutoMapper;
import com.pacoprojects.model.Produto;
import com.pacoprojects.repository.MarcaProdutoRepository;
import com.pacoprojects.repository.PessoaJuridicaRepository;
import com.pacoprojects.repository.ProdutoRepository;
import com.pacoprojects.util.GenerateMiniatureImage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final ProdutoRepository repositoryProduto;
    private final MarcaProdutoRepository repositoryMarcaProduto;
    private final PessoaJuridicaRepository repositoryJuridica;
    private final ProdutoMapper mapperProduto;
    private final EmailService emailService;
    private final GenerateMiniatureImage generateMiniatureImage;

    public List<ProdutoProjections> getAllProdutos(Long idEmpresa) {
        return repositoryProduto.findAllByEmpresa_Id(idEmpresa);
    }

    public ProdutoProjections getProdutoById(Long id) {
        return repositoryProduto.findProdutoById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Produto não foi encontrado."));
    }

    public List<ProdutoProjections> getAllProdutosByName(String name, Long idEmpresa) {
        return repositoryProduto.findAllByNomeContainsIgnoreCaseAndEmpresa_Id(name, idEmpresa);
    }

    public ProdutoDto addProduto(ProdutoDto produtoDto) {

        validateProduto(produtoDto);

        Produto entity = mapperProduto.toEntity(produtoDto);
        generateMiniatureFromProductImageBase64(entity);

        entity = repositoryProduto.save(entity);

        repositoryProduto.findById(entity.getId()).ifPresent(this::verifyStock);

        return mapperProduto.toDto(entity);
    }

    public void deleteProduto(Long id) {
        if (repositoryProduto.existsById(id)) {
            repositoryProduto.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Produto não foi encontrado.");
        }
    }

    private void verifyStock(Produto produto) {

        if (produto.getAlertaEstoqueEnabled() && produto.getQuantidadeEstoque() <= 1 && produto.getEmpresa().getEmail() != null) {
            emailService.sendMailWithAttachment(EmailObject
                    .builder()
                    .destinatario(produto.getEmpresa().getEmail())
                    .assunto("Produto com estoque baixo.")
                    .menssagem(EmailMessage
                            .getLowStockMessage(
                                    produto.getEmpresa().getNome(),
                                    produto.getNome(),
                                    produto.getQuantidadeEstoque()))
                    .build());
        }
    }

    private void generateMiniatureFromProductImageBase64(Produto produto) {
        produto.setImagemProdutos(produto.getImagemProdutos()
                .stream().peek(imagemProduto ->
                        imagemProduto.setImagemMiniatura(
                                generateMiniatureImage.getMiniature(imagemProduto.getImagemOriginal()))
                ).collect(Collectors.toList()));
    }

    private void validateProduto(ProdutoDto produtoDto) {

        if (produtoDto.empresa() == null || produtoDto.empresa().id() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Empresa deve ser informada.");
        } else if (!repositoryJuridica.existsById(produtoDto.empresa().id())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Código de empresa inválido.");
        }

        if (produtoDto.marcaProduto() == null || produtoDto.marcaProduto().id() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Marca do produto deve ser informado.");
        } else if (!repositoryMarcaProduto.existsById(produtoDto.marcaProduto().id())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Código de produto inválido.");
        }

        if (produtoDto.categorias().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Pelo menos uma categoria deve ser informado.");
        }

        if (repositoryProduto.existsByNomeIgnoreCaseAndEmpresa_Id(produtoDto.nome().trim(), produtoDto.empresa().id())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Produto já foi cadastrado.");
        }
    }
}
