# The-Music-Shop

Springboot microservices project

<br />

Vou abrir uma loja de Vinyls onde e preciso de disponibilizar um serviço estilo
Spotify para os meus clientes saberem o que tenho em stock. Como vivemos no
século 21, quero que isto seja quase tudo digital com exceção da entrega e venda
de Vinyls. Como tal, preciso de uma aplicação que tenha os seguintes requisitos:
Os clientes da minha loja podem inscrever-se na aplicação criando uma conta. (Não
há necessidade de detalhe a autenticações). 

### **Os clientes para além dos dados básicos como:** 
- nome
- idade
- sexo
- Possuem ainda dinheiro, que começa a zero.
  (O dinheiro pode ser depositado e retirado da conta).

## **O stock da minha loja é composto por Vinyls, que têm a seguinte informação:**
- Nome do Compositor
- Categoria
- Nome da Música
- Álbum
- The Music Shop 2
- Data de Entrada do Produto
- Preço
- Número de Cópias Disponíveis

Tem que haver a possibilidade de criar, atualizar e remover Vinyls.

Cada cliente pode fazer Browsing por todos os Vinyls, ou especificar uma Categoria
ou Nome. Cada cliente pode ainda alugar um Vinyl se este estiver disponível, desde
que pague o preço respetivo. Pode ainda devolver o mesmo após o ter usado.

<br />

## **Requisitos técnicos:**

- Usar Spring para a aplicação. 
- Base de Dados PostgreSQL . 
- Aplicação e BD a correr em Docker containers separados. Toda a comunicação externa da aplicação deve ser
feita por REST (Usar o Postman para invocar os serviços).

### **Notas:**

- Atenção a usar as várias camadas de uma aplicação Spring: Camada
Controlador, Business, Service, e Repository.
- Idealmente fazer toda a comunicação usando DTO's (Não é necessário para este
projeto mas vai dar jeito para o OM).
- Bonus points se o componente serviço de aluguer de Vinyls estiver separado do
componente de gestão de Clientes e Vinyls.