# House Hero

Rede social gameficada para gerenciamento de tarefas

## 🚀 Tecnologias Utilizadas

- Java
- Spring Web
- Spring Actuator
- Spring Validation
- Spring Security
- Mapstruct
- Flywaydb
- Javascript
- React
- Socket.io
- Postgres



## Recursos

Documentação da API HouseHero
=============================

A API HouseHero é uma plataforma para gerenciar tarefas e interações entre usuários.

Usuários
--------
GET [http://localhost:8080/api/activate-account](http://localhost:8080/api/activate-account)

* Obter a página para ativar uma conta de usuário

POST [http://localhost:8080/api/activate-account](http://localhost:8080/api/activate-account)

* Ativar uma conta de usuário com base no código fornecido

POST [http://localhost:8080/api/forgot-password](http://localhost:8080/api/forgot-password)

* Iniciar o processo de redefinição de senha de um usuário

POST [http://localhost:8080/api/login](http://localhost:8080/api/login)

* Fazer login em uma conta de usuário

GET [http://localhost:8080/api/logout](http://localhost:8080/api/logout)

* Fazer logout de uma conta de usuário

POST [http://localhost:8080/api/reset-password](http://localhost:8080/api/reset-password)
* Redefinir a senha de uma conta de usuário

GET [http://localhost:8080/api/users](http://localhost:8080/api/users)

* Obter informações sobre todos os usuários

POST [http://localhost:8080/api/users](http://localhost:8080/api/users)

* Criar uma nova conta de usuário

GET [http://localhost:8080/api/users/activate-account](http://localhost:8080/api/users/activate-account)

* Obter a página para ativar uma conta de usuário específica

GET [http://localhost:8080/api/users/{{id}}](http://localhost:8080/api/users/%7B%7Bid%7D%7D)

* Obter informações sobre um usuário específico


GET [http://localhost:8080/api/me](http://localhost:8080/api/me)

* Obter informações sobre a conta de usuário atualmente logada

Tarefas
--------

POST [http://localhost:8080/api/tasks](http://localhost:8080/api/tasks)

* Criar uma nova tarefa

GET [http://localhost:8080/api/tasks/{{taskId}}](http://localhost:8080/api/tasks/%7B%7BtaskId%7D%7D)

* Obter informações de uma tarefa específica

PUT [http://localhost:8080/api/tasks/{{taskId}}](http://localhost:8080/api/tasks/%7B%7BtaskId%7D%7D)

* Atualizar informações de uma tarefa específica

DELETE [http://localhost:8080/api/tasks/{{taskId}}](http://localhost:8080/api/tasks/%7B%7BtaskId%7D%7D)

* Deletar uma tarefa específica

PUT [http://localhost:8080/api/tasks/{{taskId}}/finish](http://localhost:8080/api/tasks/%7B%7BtaskId%7D%7D/finish)

* Marcar uma tarefa como concluída

PUT [http://localhost:8080/api/tasks/{{taskId}}/reset-finish](http://localhost:8080/api/tasks/%7B%7BtaskId%7D%7D/reset-finish)

* Redefinir uma tarefa para o status "não concluída"

GET [http://localhost:8080/api/users/{{userId}}/tasks](http://localhost:8080/api/users/%7B%7BuserId%7D%7D/tasks)

* Obter informações sobre todas as tarefas de um usuário específico


Missões
--------

GET [http://localhost:8080/api/quests](http://localhost:8080/api/quests)

* Obter informações sobre todas as missões disponíveis

POST [http://localhost:8080/api/quests](http://localhost:8080/api/quests)

* Criar uma nova missão

Social
--------

POST [http://localhost:8080/api/users/{{id}}/request-friendship](http://localhost:8080/api/users/%7B%7Bid%7D%7D/request-friendship)

* Solicitar amizade com um usuário específico

GET [http://localhost:8080/api/me/friendships](http://localhost:8080/api/me/friendships)

* Obter informações sobre todas as amizades da conta de usuário atualmente logada

GET [http://localhost:8080/api/me/friendships/requests](http://localhost:8080/api/me/friendships/requests)

* Obter informações sobre todas as solicitações de amizade recebidas pela conta de usuário atualmente logada

PUT [http://localhost:8080/api/me/friendships/requests](http://localhost:8080/api/me/friendships/requests)

* Responder uma solicitação de amizade

DELETE [http://localhost:8080/api/me/friendships/requests/{{idFriendship}}](http://localhost:8080/api/me/friendships/requests/%7B%7BidFriendship%7D%7D)

* Deletar uma solicitação de amizade

Loja
--------

GET [http://localhost:8080/api/items](http://localhost:8080/api/items)

* Obter informações sobre todos os itens disponíveis

POST [http://localhost:8080/api/items](http://localhost:8080/api/items)

* Criar um novo item

POST [http://localhost:8080/api/items/buy](http://localhost:8080/api/items/buy)

* Comprar um item


PUT [http://localhost:8080/api/me/avatar](http://localhost:8080/api/me/avatar)

* Atualizar o avatar da conta de usuário atualmente logada

PUT [http://localhost:8080/api/me/banner](http://localhost:8080/api/me/banner)

* Atualizar o banner da conta de usuário atualmente logada

