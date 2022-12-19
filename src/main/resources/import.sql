INSERT INTO public.produto(descricao, preco_compra, preco_venda) VALUES ('Arroz', '10.50', '16.70');
INSERT INTO public.produto(descricao, preco_compra, preco_venda) VALUES ('Feijão', '20.50', '30.70');
INSERT INTO public.produto(descricao, preco_compra, preco_venda) VALUES ('Óleo', '4.50', '10.70');
INSERT INTO public.produto(descricao, preco_compra, preco_venda) VALUES ('Farinha de Trigo', '10.50', '30.70');

INSERT INTO public.forma_pagamento(descricao) VALUES ('Cartão Visa');
INSERT INTO public.forma_pagamento(descricao) VALUES ('pix');
INSERT INTO public.forma_pagamento(descricao) VALUES ('Electron');

INSERT INTO public.cliente(cpf, nome, rg) VALUES ('99999999999', 'João da Silva', '748145481581');
INSERT INTO public.cliente(cpf, nome, rg) VALUES ('11111111111', 'Maria Joaquina', '873338787878');
INSERT INTO public.cliente(cpf, nome, rg) VALUES ('88888888889', 'Alice Braga', '87878444878');
INSERT INTO public.cliente(cpf, nome, rg) VALUES ('88888888888', 'Maria Joana', '87878787878');

INSERT INTO public.pedido(id_cliente, id_forma_pagamento) VALUES (1, 1);
INSERT INTO public.item_pedido(quantidade, valor_item, id_pedido, id_produto) VALUES ('10', '20', 1, 1);
INSERT INTO public.item_pedido(quantidade, valor_item, id_pedido, id_produto) VALUES ('10', '20', 1, 2);


INSERT INTO public.role(nome_role) VALUES ('ROLE_ADMIN');
INSERT INTO public.role(nome_role) VALUES ('ROLE_CADASTRADOR');
INSERT INTO public.role(nome_role) VALUES ('ROLE_PEDIDO');

INSERT INTO public.usuario(login, nome, senha) VALUES ('emanuell', 'Emanuel Lopes', '$2a$10$8okjbHGqFzdFAX.XtVBO6eEZWi4F7W285Yy6ZsQC9ZyecUB.gO91C'); -- 102030
INSERT INTO public.usuarios_role(usuario_id, role_id) VALUES (1, 1);

INSERT INTO public.usuario(login, nome, senha) VALUES ('admcadastrador', 'Adm Cadastrador', '$2a$10$umA2cp3zGaSMPAGreI3PKePL9eZaDcravDUIONL./dbk1g.LQS7hW'); -- 203040
INSERT INTO public.usuarios_role(usuario_id, role_id) VALUES (2, 2);

INSERT INTO public.usuario(login, nome, senha) VALUES ('admpedidos', 'Admin Pedidos', '$2a$10$umA2cp3zGaSMPAGreI3PKePL9eZaDcravDUIONL./dbk1g.LQS7hW'); -- 203040
INSERT INTO public.usuarios_role(usuario_id, role_id) VALUES (2, 2);








