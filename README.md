#  👨‍💻 Java JPA Hibernate 🧑‍💻

<b>Introdução:</b>
- JPA e suas implementações;
- Ciclo de vida de uma entidade;
- Dependências;
- Configuração do arquivo <b>persistence.xml:</b>
- Executando projeto

##

### 1 - JPA e suas implementações:

<b>JPA (Java Persistence API)</b> é uma especificação e possue algumas implementações como o <b>Hibernate</b>, <b>EclipseLink</b> e <b>OpenJPA</b>. 
O <b>Hibernate</b> é a implementação mais usada atualmente no mercado.

![image](https://user-images.githubusercontent.com/74054701/209901789-342b8da3-8c62-452d-9f72-737d202c2809.png)
##
### 2 - Ciclo de vida de uma entidade:

- <b>TRANSIENT:</b> Entidade que <b>nunca</b> foi <b>persistida</b>, ou seja, entidade que não está gravada no banco de dados.
- <b>MANAGED:</b> <b>Managed</b> é o principal estado que uma entidade pode estar, portanto, tudo que acontece com uma entidade nesse estado, a <b>JPA observará</b> e poderá sincronizar com o banco de dados, a depender do que fizermos.
- <b>DETACHED:</b> o <b>Detached</b> é um estado em que a entidade <b>não</b> é <b>mais transient</b>, porque <b>tem id</b>, já foi salva no banco de dados, porém, não está mais sendo gerenciada. Portanto, se mexermos nos atributos, a <b>JPA não disparará update</b> e <b>nem fará mais nada</b>.
- <b>REMOVED:</b> estado de uma entidade <b>removida</b>.

![image](https://user-images.githubusercontent.com/74054701/209910879-7ce76282-6847-4b14-a4b9-c672b95b3e78.png)

- <b>persist():</b> move do estado <b>TRANSIENT</b> para o estado <b>MANAGED</b>.
- <b>clear():</b> para <b>limpar</b> as entidades gerenciadas do <b>EntityManager</b>, o modo muda de estado. Se ela estava salva antes, passa para um estado chamado de <b>DETACHED</b>.
- <b>close():</b> fechar o <b>EntityManager</b>, o modo muda de estado. Se ela estava salva antes, passa para um estado chamado de <b>DETACHED</b>.
- <b>flush():</b> <b>JPA sincronizará</b> as informações com o banco de dados.
- <b>commit():</b> JPA sincronizará e confirmará com o banco de dados as operações(insert/update/delete) realizadas numa transação.
- <b>merge():</b> move do estado <b>DETACHED</b> para o estado <b>MANAGED</b>.
- <b>remove():</b> para exclusão da entidade, move do estado <b>MANAGED</b> para o estado <b>REMOVED</b>.


##
### 3 - Dependências
- Hibernate
- H2 Database
~~~
    <dependencies>
        <dependency>
            <groupId>org.hibernate</groupId>
            <artifactId>hibernate-entitymanager</artifactId>
            <version>5.4.27.Final</version>
        </dependency>

        <dependency>
            <groupId>com.h2database</groupId>
            <artifactId>h2</artifactId>
            <version>1.4.200</version>
        </dependency>
    </dependencies>
~~~
##
### 4 - Configuração do arquivo <b>persistence.xml:</b>
Na <b>JPA</b>, as configurações ficam no arquivo <b>.xml</b>. É possível configurar também via código Java, mas, geralmente, ficam no arquivo <b>persistence.xml</b>.

- Criei uma pasta  <b>META-INF</b> dentro da pasta <b>resources</b>
- Dentro da pasta <b>META-INF</b> criei um arquivo <b>persistence.xml</b>

![image](https://user-images.githubusercontent.com/74054701/209903145-b890a4f2-fcb4-450f-96f5-fc46de4dbc6d.png)
~~~
<?xml version="1.0" encoding="UTF-8"?>
<persistence version="2.2"
             xmlns="http://xmlns.jcp.org/xml/ns/persistence"
             xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
             xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/persistence http://xmlns.jcp.org/xml/ns/persistence/persistence_2_2.xsd">
    
    <persistence-unit name="loja" transaction-type="RESOURCE_LOCAL">
        <properties>
            <property name="javax.persistence.jdbc.driver" value="org.h2.Driver"/>
            <property name="javax.persistence.jdbc.url" value="jdbc:h2:mem:loja"/>
            <property name="javax.persistence.jdbc.user" value="sa"/>
            <property name="javax.persistence.jdbc.password" value=""/>
            <property name="hibernate.dialect" value="org.hibernate.dialect.H2Dialect"/>
            <property name="hibernate.show_sql" value="true"/>
            <property name="hibernate.hbm2ddl.auto" value="update"/>
        </properties>
    </persistence-unit>
</persistence>

~~~
### Explicação sobre a estrutura do arquivo <b>persistence.xml</b>:

Dentro da tag persistene-unit temos os atributos name e transaction-type:
- <b>name:</b> podemos colocar o nome que quisermos
- <b>transaction-type:</b> possui dois valores possíveis: <b>JTA</b> ou <b>RESOURCE LOCAL</b>.

 1 - <b>RESOURCE LOCAL</b>: nesse projeto estamos utilizando o <b>"RESOURCE LOCAL"</b>, ou seja, o <b>iniciar</b> e o <b>commit</b> da <b>transação</b> ficará por nossa conta toda vez que realizarmos operações de escrita no banco de dados como insert, update e delete.
 
 2 - <b>JTA:</b> é usado quando estamos utilizando um <b>servidor</b> de <b>aplicação</b>, quando vai trabalhar com <b>EJB</b>, <b>JMS</b> ou outras tecnologias do <b>Java EE</b>, e o <b>servidor</b> se encarrega de <b>cuidar</b> da <b>transação</b>.
~~~
<persistence-unit name="loja" transaction-type="RESOURCE_LOCAL">
~~~

- Informaremos qual a <b>classe</b> do <b>driver</b> do <b>JDBC</b>:
~~~
<property name="javax.persistence.jdbc.driver" value="org.h2.Driver"/>
~~~

- <b>Endereço</b> de <b>conexão</b> com o banco de dados:
~~~
<property name="javax.persistence.jdbc.url" value="jdbc:h2:mem:loja"/>
~~~

- Informaremos <b>usuário</b> e a <b>senha</b> para acesso ao banco de dados:
~~~
<property name="javax.persistence.jdbc.user" value="sa"/>
<property name="javax.persistence.jdbc.password" value=""/>
~~~

### Propriedades Específicas
Cada uma das implementações da JPA possuem propriedades específicas. Como estamos utilizando o Hibernate, podemos colocar algumas propriedades específicas dele.

- A propriedade que terá o nome <b>"hibernate.dialect"</b>, iremos informar, pois o <b>Hibernate</b> precisa saber qual é a <b>classe</b> que tem o <b>dialeto</b> do banco de dados. Ou seja, saber das particularidades do banco de dados. Por exemplo, no H2 não existe booleano (booleano é inteiro, 0 e 1), no MySQL existe:
~~~
<property name="hibernate.dialect" value="org.hibernate.dialect.H2Dialect"/>
~~~

- Por padrão vem <b>falso</b>, mas se quisermos que <b>mostre</b> no <b>console</b> as <b>instruções SQL</b> é só alteramos para <b>true</b>:
~~~
<property name="hibernate.show_sql" value="true"/>
~~~

- Por padrão vem <b>falso</b>, mas se quisermos que <b>formate</b> as <b>instruções SQL</b> que serão exibidas no <b>console</b> é só alteramos para <b>true</b>:
~~~
<property name="hibernate.format_sql" value="true"/>
~~~

- Para essa propriedade <b>"hibernate.hbm2ddl.auto"</b> podemos passar os seguintes valores:\
 1 - <b>create:</b> o <b>Hibernate</b> vai olhar as entidades e gerar o comando para criar o banco de dados. Portanto, ele vai apagar tudo e criar do zero as tabelas. Após usarmos a aplicação, ele não apagará as tabelas, elas continuarão lá.\
 2 - <b>create-drop:</b> o <b>Hibernate</b> cria as tabelas quando rodarmos a aplicação e, depois que terminamos de executar a aplicação, ele imediatamente dropa.\
 3 - <b>update:</b> não irá apagar os dados das tabelas, apenas irá atualizar as tabelas caso tenha alguma mudança.
 
~~~
<property name="hibernate.hbm2ddl.auto" value="update"/>
~~~
##
### 5 - Executando projeto

Exemplo utilizando IDE intellij idea:
![image](https://user-images.githubusercontent.com/74054701/209914527-99ed7bc8-e378-42f8-8f17-1aed674ba5ae.png)

![image](https://user-images.githubusercontent.com/74054701/209914629-ca51bc58-c51e-49e2-8b21-9ae8c38179ca.png)



