Dependencias:
spring-boot-starter-data-jpa
spring-boot-starter-web
mysql-connector-j
lombok
jdk 21

Como rodar:
.\mvnw spring-boot:run




Rotina de teste com comandos curls que podem ser usados no postman:
curl -X POST http://localhost:8080/api/categorias -H "Content-Type: application/json" -d '{"nome": "Eletrônicos"}'

curl -X POST http://localhost:8080/api/categorias -H "Content-Type: application/json" -d '{"nome": "Livros"}'

curl -X GET http://localhost:8080/api/categorias

curl -X PUT http://localhost:8080/api/categorias/2 -H "Content-Type: application/json" -d '{"nome": "Literatura e HQs"}'

curl -X POST http://localhost:8080/api/fornecedores -H "Content-Type: application/json" -d '{"nome": "Tech Distribuidora Global"}'

curl -X POST http://localhost:8080/api/fornecedores -H "Content-Type: application/json" -d '{"nome": "Editora Alpha"}'

curl -X GET http://localhost:8080/api/fornecedores

curl -X PUT http://localhost:8080/api/fornecedores/1 -H "Content-Type: application/json" -d '{"nome": "Tech Global Ltda"}'

curl -X POST http://localhost:8080/api/clientes -H "Content-Type: application/json" -d '{"nome": "João Silva", "cpf": "123.456.789-00", "email": "joao@email.com"}'

curl -X GET http://localhost:8080/api/clientes

curl -X PUT http://localhost:8080/api/clientes/1 -H "Content-Type: application/json" -d '{"nome": "João da Silva", "cpf": "123.456.789-99", "email": "joao.novo@email.com"}'

curl -X GET http://localhost:8080/api/clientes/1

curl -X POST http://localhost:8080/api/produtos -H "Content-Type: application/json" -d '{"nome": "Notebook Gamer", "preco": 4500.00, "categoria": {"id": 1}, "fornecedores": [{"id": 1}]}'

curl -X POST http://localhost:8080/api/produtos -H "Content-Type: application/json" -d '{"nome": "Mouse Sem Fio", "preco": 150.00, "categoria": {"id": 1}, "fornecedores": [{"id": 1}]}'

curl -X GET http://localhost:8080/api/produtos

curl -X PUT http://localhost:8080/api/produtos/2 -H "Content-Type: application/json" -d '{"nome": "Mouse Bluetooth", "preco": 180.00, "categoria": {"id": 1}, "fornecedores": [{"id": 1}]}'

curl -X PATCH http://localhost:8080/api/produtos/1/estoque -H "Content-Type: application/json" -d '{"quantidade": 50}'

curl -X PATCH http://localhost:8080/api/produtos/2/estoque -H "Content-Type: application/json" -d '{"quantidade": 100}'

curl -X GET http://localhost:8080/api/produtos

curl -X POST http://localhost:8080/api/vendas -H "Content-Type: application/json" -d '{"clienteId": 1, "itens": [{"produtoId": 1, "quantidade": 2}, {"produtoId": 2, "quantidade": 5}]}'

curl -X GET http://localhost:8080/api/vendas

curl -X GET http://localhost:8080/api/vendas/1

curl -X GET http://localhost:8080/api/produtos/1

curl -X DELETE http://localhost:8080/api/vendas/1

curl -X DELETE http://localhost:8080/api/produtos/2

curl -X DELETE http://localhost:8080/api/clientes/1

curl -X DELETE http://localhost:8080/api/fornecedores/2

curl -X DELETE http://localhost:8080/api/categorias/2