# Guia de Uso do GitHub — Fluxo Colaborativo da Equipe

### Este documento define o padrão que será utilizado pela equipe para trabalhar de forma colaborativa no GitHub, organizando as branches, commits e merges do projeto.

### O objetivo é evitar conflitos, manter a main estável e deixar claro onde cada pessoa deve desenvolver e enviar suas alterações.

# 🌿 Estrutura de Branches

### O projeto será organizado em três tipos principais de branches:

# 1. Primeiro passo: clonar o projeto

Antes de começar a desenvolver, todos os integrantes devem clonar o repositório do projeto para o próprio computador.

O clone cria uma cópia local do projeto, permitindo que cada integrante trabalhe no código e depois envie suas alterações para o GitHub.

No GitHub, copie a URL do repositório e execute:

```
git clone URL_DO_REPOSITORIO
```

Depois, entre na pasta do projeto:

```
cd nome-do-projeto
```

A partir desse momento, o projeto estará disponível localmente no seu computador.

Exemplo:

```
git clone https://github.com/equipe/projeto.git
cd projeto
```

### **Importante:** cada integrante deve fazer o clone do mesmo repositório oficial do projeto. Não é necessário criar um novo repositório para cada pessoa.

## 2. Branches individuais

Cada integrante da equipe possui uma branch própria.

O nome da branch deve ser o nome da pessoa em letras minúsculas, sem espaços ou caracteres especiais.

Exemplos:

```
joao
maria
pedro
ana
```

Cada pessoa deve trabalhar e realizar seus commits somente na sua própria branch individual.

Por exemplo:

```
João → branch joao
Maria → branch maria
Pedro → branch pedro
```

Portanto, se João desenvolver uma nova funcionalidade, suas alterações deverão ser commitadas na:

```
joao
```

# 3. Branches por área/função

Também existirão branches que representam as principais áreas do projeto.

Exemplo:

```
front_end
back_end
docs
```

Essas branches não são destinadas ao desenvolvimento individual do dia a dia.

Elas servirão para reunir as alterações que já foram desenvolvidas e consideradas prontas dentro de determinada área.

Por exemplo, se:

```
joao → back_end
maria → back_end
```

João e Maria desenvolverão suas funcionalidades separadamente em:

```
joao
maria
```

Quando determinada funcionalidade estiver pronta e aprovada, ela poderá ser integrada à:

```
back_end
```

# 4 . Branch main

### A branch main representa a versão principal do projeto.

Ninguém deve realizar commits diretamente na main.

A main será utilizada somente para receber merges das branches de área, após as alterações terem passado pelo processo definido pela equipe.

Fluxo geral:

```
Branch individual
       ↓
Branch da área
       ↓
     main
```

Por exemplo:

```
joao
  ↓
back_end
  ↓
main
```

# 🔄 Fluxo de Trabalho

O fluxo padrão de desenvolvimento será:

```

1. Criar/desenvolver a funcionalidade
              ↓
2. Trabalhar na sua branch individual
              ↓
3. Fazer commits das alterações
              ↓
4. Finalizar a funcionalidade
              ↓
5. Revisar/testar a funcionalidade
              ↓
6. Fazer merge para a branch da área
              ↓
7. Integrar a branch da área na main

```

### Exemplo prático

Suponha que João e Maria façam parte do desenvolvimento do Back-End.

As branches serão:

```
joao
maria
back_end
main
```

João desenvolve uma funcionalidade:

```
joao
```

e realiza seus commits normalmente nessa branch.

Maria trabalha em outra funcionalidade:

```
maria
```

Quando a funcionalidade de João estiver pronta, após os testes e revisão necessários, será feito:

```
joao → back_end
```

Quando a funcionalidade de Maria também estiver pronta:

```
maria → back_end
```

Assim, a branch back_end passa a reunir as funcionalidades concluídas do Back-End.

Posteriormente, quando for decidido que a versão do Back-End está pronta para ser integrada ao projeto principal:

```
back_end → main
```

# 🚫 Regra mais importante: não commitar na main

### **NUNCA faça commits diretamente na main.**

Não utilize:

```
git checkout main
git add .
git commit -m "minha alteração"
```

para desenvolver uma funcionalidade.

A main deve permanecer protegida e receber alterações somente através de merge.

Isso ajuda a:

- Evitar que alterações incompletas cheguem ao projeto principal.

- Reduzir conflitos entre integrantes.

- Manter a main mais estável.

- Facilitar a identificação de quem desenvolveu cada funcionalidade.

Permitir que cada área seja validada antes de chegar à versão principal.

# 💾 Commits

Cada integrante deve realizar seus commits na própria branch.

Exemplo para João:

```
git checkout joao

git add *

git commit -m "Adiciona autenticação de usuários"

git push origin joao
```

Os commits devem possuir mensagens claras e objetivas, explicando o que foi alterado.

Bons exemplos

- Adiciona tela de login
- Corrige validação de email
- Implementa endpoint de usuários
- Atualiza documentação da API
- Corrige erro no cadastro

Evite mensagens genéricas

```
alterações
coisas
teste
mudanças
aaa
update
```

Uma boa mensagem de commit facilita entender o histórico do projeto posteriormente.

# 🔀 Merges

O merge é utilizado para transferir as alterações de uma branch para outra.

No nosso fluxo, normalmente teremos:

```
individual → área → main
```

Exemplo:

```
joao → back_end → main
```

O merge para uma branch de área deve acontecer quando a funcionalidade estiver pronta para ser integrada, de acordo com os critérios definidos pela equipe.

Antes de realizar o merge, é recomendado:

- Verificar se a funcionalidade está funcionando.

- Testar as alterações.

- Verificar se não existem erros conhecidos.

- Conferir se as alterações não quebram outras funcionalidades.

- Resolver possíveis conflitos.

Solicitar revisão de outro integrante, quando aplicável.

# 👥 Pull Requests

Sempre que possível, os merges devem ser realizados através de Pull Requests (PRs) no GitHub.

Por exemplo:

```
joao → back_end
```

pode ser realizado através de um Pull Request com uma descrição explicando:

- O que foi desenvolvido.

- Qual problema foi resolvido.

- Como a funcionalidade foi testada.

- Se existe alguma alteração importante que os outros integrantes precisam conhecer.

Isso permite que outros membros da equipe revisem o código antes da integração.

# 🔄 Antes de começar a trabalhar

Antes de iniciar uma nova tarefa, é recomendado atualizar sua branch com o estado mais recente do projeto, conforme o fluxo definido pela equipe.

Também é importante verificar se alguém já está trabalhando na mesma funcionalidade para evitar trabalho duplicado.

# ⚠️ Conflitos de Merge

Conflitos podem acontecer quando duas pessoas modificam a mesma parte de um arquivo.

Por exemplo:

```
João → modifica arquivo X
Maria → modifica a mesma parte do arquivo X
```

Nesse caso, o Git pode não conseguir decidir automaticamente qual alteração deve permanecer.

Não simplesmente escolha uma das alterações sem verificar o que foi feito.

- Quando houver conflito:

- Identifique quais alterações estão em conflito.

- Converse com os integrantes envolvidos, se necessário.

- Decida quais alterações devem permanecer.

- Resolva o conflito.

- Teste novamente o projeto.

Só então finalize o merge.

# 📌 Regras resumidas

Para facilitar, estas são as principais regras que todos devem seguir:

- Cada integrante trabalha na sua própria branch.

- O nome da branch individual deve ser o nome da pessoa em minúsculo.

- João trabalha na joao, Maria na maria, etc.

- Branches como front_end, back_end e docs representam as áreas do projeto.

- As branches de área recebem funcionalidades que já estão prontas para integração.

- A main não deve receber commits diretos.

- A main recebe alterações por meio de merges.

- Faça commits frequentes e com mensagens claras.

- Antes de integrar uma funcionalidade, teste e revise as alterações.

- Utilize Pull Requests sempre que possível.

- Não sobrescreva alterações de outros integrantes sem verificar com eles.

- Em caso de conflito, converse com os responsáveis pelas alterações antes de resolver.

- Não faça alterações diretamente em uma branch que pertence a outro integrante sem combinar previamente.

- Mantenha sua branch atualizada e envie (push) seus commits para o GitHub regularmente.

# 🗂️ Estrutura visual

A organização esperada do projeto pode ser representada assim:

                         ┌──────────┐
                         │   main   │
                         └────▲─────┘
                              │
                    Merge da área concluída
                              │
               ┌──────────────┼──────────────┐
               │              │              │
          ┌────┴────┐    ┌────┴────┐    ┌────┴────┐
          │back_end │    │front_end│    │  docs   │
          └────▲────┘    └────▲────┘    └────▲────┘
               │              │              │
          ┌────┴────┐    ┌────┴────┐    ┌────┴────┐
          │  joao   │    │  maria  │    │  pedro  │
          └─────────┘    └─────────┘    └─────────┘

Cada pessoa desenvolve na sua própria branch, as funcionalidades concluídas são integradas à branch correspondente à área, e posteriormente as áreas podem ser integradas à main.

Regra de ouro:

### Desenvolva na sua branch → integre na branch da área → depois integre na main.

# NUNCA faça commit diretamente na main.
