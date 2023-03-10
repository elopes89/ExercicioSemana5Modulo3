package br.com.futurodev.primeiraapi.controllers;

import br.com.futurodev.primeiraapi.dto.ItemPedidoRepresentationModel;
import br.com.futurodev.primeiraapi.dto.PedidoRepresentationModel;
import br.com.futurodev.primeiraapi.input.PedidoInput;
import br.com.futurodev.primeiraapi.model.ItemPedido;
import br.com.futurodev.primeiraapi.model.Pedido;
import br.com.futurodev.primeiraapi.service.*;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/pedidos")
public class PedidoController {


    @Autowired
    private CadastroPedidoService cadastroPedidoService;

    @Autowired
    private CadastroClienteService cadastroClienteService;

    @Autowired
    private CadastroFormaPagamentoService cadastroFormaPagamentoService;

    @Autowired
    private CadastroProdutoService cadastroProdutoService;

    @Autowired
    private CadastroItemPedidoService cadastroItemPedidoService;

    @ApiOperation("CADASTRAR")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Pedido cadastrado")
            , @ApiResponse(code = 401, message = "Usuário sem permissão para acessar o recurso")
            , @ApiResponse(code = 403, message = "Proibido")
            , @ApiResponse(code = 404, message = "Recurso não encontrado")})
    @PostMapping
    public ResponseEntity<PedidoRepresentationModel> cadastrar(@ApiParam(value = "Pedido cadastrado") @RequestBody PedidoInput pedidoInput) {
        Pedido pedido = cadastroPedidoService.salvar(toDomainObject(pedidoInput));
        return new ResponseEntity<PedidoRepresentationModel>(toRepresentatioModel(pedido), HttpStatus.CREATED);
    }

    @ApiOperation("ALTERAR")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Produto alterada")
            , @ApiResponse(code = 401, message = "Usuário sem permissão para acessar o recurso")
            , @ApiResponse(code = 403, message = "Proibido")
            , @ApiResponse(code = 404, message = "Recurso não encontrado")})
    @PutMapping
    public ResponseEntity<PedidoRepresentationModel> atualizar(@ApiParam(value = "Pedido editado", example = "1") @RequestBody PedidoInput pedidoInput) {
        Pedido pedido = cadastroPedidoService.salvar(toDomainObject(pedidoInput));
        return new ResponseEntity<PedidoRepresentationModel>(toRepresentatioModel(pedido), HttpStatus.OK);
    }

    @ApiOperation("EXCLUIR")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Pedido excluído")
            , @ApiResponse(code = 401, message = "Usuário sem permissão para acessar o recurso")
            , @ApiResponse(code = 403, message = "Proibido")
            , @ApiResponse(code = 404, message = "Recurso não encontrado")})
    @DeleteMapping
    @ResponseBody
    public ResponseEntity<String> delete(@ApiParam(value = "Id Pedido: ", example = "1") @RequestParam Long idPedido) {
        cadastroPedidoService.deletePedidoById(idPedido);
        return new ResponseEntity<String>("Pedido de ID: " + idPedido + " deletado.", HttpStatus.OK);
    }

    @ApiOperation("OBTER USUÁRIO POR ID")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Pedido encontrado")
            , @ApiResponse(code = 401, message = "Usuário sem permissão para acessar o recurso")
            , @ApiResponse(code = 403, message = "Proibido")
            , @ApiResponse(code = 404, message = "Recurso não encontrado")})
    @GetMapping(value = "/{idPedido}")
    public ResponseEntity<PedidoRepresentationModel> getPedidoById(@ApiParam(value = "Pedido deletado", example = "1") @PathVariable(value = "idPedido") Long idPedido) {

        PedidoRepresentationModel pedidoRepresentationModel =
                toRepresentatioModel(cadastroPedidoService.getPedidoById(idPedido));

        return new ResponseEntity<PedidoRepresentationModel>(pedidoRepresentationModel, HttpStatus.OK);

    }

    @ApiOperation("LISTAR TODOS POEDIDOS")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Lista de pedidos")
            , @ApiResponse(code = 401, message = "Usuário sem permissão para acessar o recurso")
            , @ApiResponse(code = 403, message = "Proibido")
            , @ApiResponse(code = 404, message = "Recurso não encontrado")})
    @GetMapping
    @ResponseBody
    public ResponseEntity<List<PedidoRepresentationModel>> getPedidos() {
        List<Pedido> pedidos = cadastroPedidoService.getPedidos();

        return
                new ResponseEntity<List<PedidoRepresentationModel>>(toCollectionRepresentationModel(pedidos), HttpStatus.OK);
    }

    @GetMapping(value = "/cliente/{idCliente}")
    public ResponseEntity<List<PedidoRepresentationModel>> getPedidoByIdCliente(
            @PathVariable(name = "idCliente") Long idCliente) {
        List<Pedido> pedidos = cadastroPedidoService.getPedidoByIdCliente(idCliente);
        List<PedidoRepresentationModel> pedidosRM = toCollectionRepresentationModel(pedidos);

        return new ResponseEntity<List<PedidoRepresentationModel>>(pedidosRM, HttpStatus.OK);
    }

    // TODO Falta ajuste neste método

    @DeleteMapping(value = "/{idPedido}/item/{idItemPedido}")
    @ResponseBody
    public ResponseEntity<String> deleteItemPedidoById(@PathVariable(name = "idPedido") Long idPedido,
                                                       @PathVariable(name = "idItemPedido") Long idItemPedido) {
        Pedido pedido = cadastroPedidoService.getPedidoById(idPedido);

        for (int i = 0; i < pedido.getItensPedido().size(); i++) {
            if (pedido.getItensPedido().get(i).getId() == idItemPedido) {

                ItemPedido itemPedido = pedido.getItensPedido().remove(i);
                itemPedido.setPedido(null);
                itemPedido.setProduto(null);
                pedido.getItensPedido().clear();
                cadastroItemPedidoService.deleteItemPedido(itemPedido);
            }
        }

        return new ResponseEntity<String>("Item de ID: " + idItemPedido + " deletado.", HttpStatus.OK);
    }


    private PedidoRepresentationModel toRepresentatioModel(Pedido pedido) {
        PedidoRepresentationModel pedidoRepresentationModel = new PedidoRepresentationModel();
        pedidoRepresentationModel.setId(pedido.getId());
        pedidoRepresentationModel.setIdCliente(pedido.getCliente().getId());
        pedidoRepresentationModel.setNomeCliente(pedido.getCliente().getNome());
        pedidoRepresentationModel.setIdFormaPagamento(pedido.getFormaPagamento().getId());
        pedidoRepresentationModel.setFormaPagamentoDescricao(pedido.getFormaPagamento().getDescricao());

        for (int i = 0; i < pedido.getItensPedido().size(); i++) {
            ItemPedidoRepresentationModel itemPedidoRepresentationModel = new ItemPedidoRepresentationModel();
            itemPedidoRepresentationModel.setId(pedido.getItensPedido().get(i).getId());
            itemPedidoRepresentationModel.setIdProduto(pedido.getItensPedido().get(i).getProduto().getId());
            itemPedidoRepresentationModel.setDescricaoProduto(pedido.getItensPedido().get(i).getProduto().getDescricao());
            itemPedidoRepresentationModel.setQuantidade(pedido.getItensPedido().get(i).getQuantidade());
            itemPedidoRepresentationModel.setValorItem(pedido.getItensPedido().get(i).getValorItem());

            pedidoRepresentationModel.getItensPedidoRepresentationModel().add(itemPedidoRepresentationModel);

        }


        return pedidoRepresentationModel;
    }

    private Pedido toDomainObject(PedidoInput pedidoInput) {

        Pedido pedido = new Pedido();

        pedido.setId(pedidoInput.getIdPedido());

        pedido.setCliente(cadastroClienteService.getClienteById(pedidoInput.getIdCliente()));
        pedido.setFormaPagamento(cadastroFormaPagamentoService.getFormaPagamentoById(pedidoInput.getIdFormaPagamento()));

        for (int i = 0; i < pedidoInput.getItensPedido().size(); i++) {

            ItemPedido itemPedido = new ItemPedido();
            itemPedido.setId(pedidoInput.getItensPedido().get(i).getId());
            itemPedido.setPedido(pedido);
            itemPedido.setProduto(cadastroProdutoService.getProdutoById(pedidoInput.getItensPedido().get(i).getIdProduto()));

            if (pedidoInput.getItensPedido().get(i).getId() == null) {
                itemPedido.setValorItem(cadastroProdutoService
                        .getProdutoById(pedidoInput
                                .getItensPedido().get(i)
                                .getIdProduto()).getPrecoVenda());
            } else {
                itemPedido.setValorItem(pedidoInput.getItensPedido().get(i).getPrecoVenda());
            }
            itemPedido.setQuantidade(pedidoInput.getItensPedido().get(i).getQuantidade());
            pedido.getItensPedido().add(itemPedido);

        }
        return pedido;
    }

    private List<PedidoRepresentationModel> toCollectionRepresentationModel(List<Pedido> pedidos) {
        return pedidos.stream()
                .map(Pedido -> toRepresentatioModel(Pedido))
                .collect(Collectors.toList());
    }
}
