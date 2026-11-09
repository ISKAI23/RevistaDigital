INSERT INTO categorias (nome, slug) VALUES ('Tecnologia', 'tecnologia');
INSERT INTO categorias (nome, slug) VALUES ('Cultura & Arte', 'cultura-e-arte');
INSERT INTO categorias (nome, slug) VALUES ('Estilo de Vida', 'estilo-de-vida');

INSERT INTO usuarios(nome, email, senha_hash, acesso) VALUES ('Ana Souza', 'ana.admin@revista.com', '$2y$10$e0MYzXyjpJS7Pd0RVvHwHe1a.hashficticio123', 'admin');
INSERT INTO usuarios(nome, email, senha_hash, acesso) VALUES ('Carlos Eduardo', 'carlos.autor@revista.com', '$2y$10$e0MYzXyjpJS7Pd0RVvHwHe1a.hashficticio123', 'autor');
INSERT INTO usuarios(nome, email, senha_hash, acesso) VALUES ('Mariana Lima', 'mariana.leitora@gmail.com', '$2y$10$e0MYzXyjpJS7Pd0RVvHwHe1a.hashficticio123', 'leitor');

INSERT INTO noticias (titulo, usuario_id, slug, status, categoria_id, publicado_em) VALUES ('O Impacto da IA no Design Digital', 2, 'o-impacto-da-ia-no-design-digital', 'PUBLICADO', 1, CURRENT_TIMESTAMP);
INSERT INTO noticias (titulo, usuario_id, slug, status, categoria_id, publicado_em) VALUES ('Nova Exposição de Arte Contemporânea Abre no Fim de Semana', 2, 'nova-exposicao-de-arte-contemporanea-abre-no-fim-de-semana', 'RASCUNHO', 1, CURRENT_TIMESTAMP);

INSERT INTO conteudos (noticia_id, tipo_secao, conteudo, ordem) VALUES (1, 'subtitulo', 'A Evolução das Ferramentas Visuais', 1);
INSERT INTO conteudos (noticia_id, tipo_secao, conteudo, ordem) VALUES (1, 'texto', 'Nos últimos anos, o uso de algoritmos generativos revolucionou a criação de protótipos e layouts. Designers agora utilizam ferramentas automatizadas para acelerar tarefas repetitivas.', 2);
INSERT INTO conteudos (noticia_id, tipo_secao, conteudo, ordem) VALUES (1, 'imagem', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT_hevs5zMc4dtSUsgIT18SQlzL7boK0AyWr-gweF6SCDgzxRJDTeYtztTh&s=10', 3);
INSERT INTO conteudos (noticia_id, tipo_secao, conteudo, ordem) VALUES (1, 'subtitulo', 'Automação vs Criatividade Humana', 4);
INSERT INTO conteudos (noticia_id, tipo_secao, conteudo, ordem) VALUES (1, 'texto', 'Apesar do receio inicial sobre o fim de algumas profissões, especialistas destacam que a tecnologia atua como uma assistente criativa, permitindo foco em estratégia e experiência do utilizador.', 5);
INSERT INTO conteudos (noticia_id, tipo_secao, conteudo, ordem) VALUES (2, 'subtitulo', 'Artistas Latino-Americanos em Destaque', 1);
INSERT INTO conteudos (noticia_id, tipo_secao, conteudo, ordem) VALUES (2, 'texto', 'A mostra reúne mais de 40 obras inéditas de novos talentos da pintura e da escultura abstrata.', 2);
INSERT INTO conteudos (noticia_id, tipo_secao, conteudo, ordem) VALUES (2, 'imagem', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS-PkdmwWlrXMKP-BJ-23vcHtHs-5aDNY256CBqRG3yuTlFqncoG4KhE6GI&s=10', 3);
INSERT INTO conteudos (noticia_id, tipo_secao, conteudo, ordem) VALUES (2, 'texto', 'A entrada é gratuita durante todo o fim de semana de abertura, com visitas guiadas pelos próprios curadores.', 4);

INSERT INTO imagem (url, noticia_id, alt_text, criado_em) VALUES ('https://exemplo.com/imagens/noticia1.jpg', 1, 'Imagem de uma notícia', CURRENT_TIMESTAMP);
INSERT INTO imagem (url, noticia_id, alt_text, criado_em) VALUES ('https://exemplo.com/imagens/noticia2.png', 2, 'Imagem de uma fábrica', CURRENT_TIMESTAMP);
INSERT INTO imagem (url, alt_text, criado_em) VALUES('https://exemplo.com/imagens/noticia3.jpg', 'Imagem de um evento', CURRENT_TIMESTAMP);
INSERT INTO imagens (url, alt_text, criado_em) VALUES ('https://suarerevista.com/uploads/ia-futuro.jpg', 'Ilustração de circuitos e inteligência artificial', CURRENT_TIMESTAMP);
INSERT INTO imagens (url, alt_text, criado_em) VALUES ('https://suarerevista.com/uploads/exposicao-arte.jpg', 'Galeria de arte com quadros modernos', CURRENT_TIMESTAMP);