# Project Nexus - Biblioteca de Segurança para Geração de Tokens de Acesso

uma biblioteca de segurança desenvolvida como parte do projeto Nexus. O objetivo principal desta biblioteca é fornecer funcionalidades para geração de tokens de acesso, além de integrar-se ao Spring Security para facilitar a implementação de autenticação e autorização em projetos Java.

## Funcionalidades Principais

- **Geração de Tokens JWT**: A Lib utiliza a API Java-JWT para gerar tokens JSON Web Token (JWT) de forma segura e eficiente.
- **Geração de funcionalidades do Spring Security**: Metodos de autenticação e permissoes facilitadas para utilização em outros projetos
- **Testes Abrangentes**: A biblioteca é acompanhada por testes unitários para garantir o correto funcionamento das funcionalidades.

## Como usar

Para usar esta biblioteca, você precisa adicionar as seguintes dependências e repositórios ao seu arquivo `pom.xml`:

### Dependências

Inclua a seguinte dependência dentro da tag `<dependencies>` do seu `pom.xml`:

```xml
<dependencies>
    <dependency>
        <groupId>com.nexus</groupId>
        <artifactId>security</artifactId>
        <version>0.0.1-SNAPSHOT</version>
    </dependency>
    <!-- outras dependências aqui -->
</dependencies>
```

Inclua a seguinte dependência dentro da tag `<repositories>` do seu `pom.xml`:

```xml
<repositories>
    <repository>
        <id>lib-security</id>
        <url>https://raw.githubusercontent.com/igorcampos-dev/nexus.java.lib.security/master/target/</url>
    </repository>
    <!-- outros repositórios aqui -->
</repositories>

```