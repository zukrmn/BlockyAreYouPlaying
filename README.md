# BlockyAreYouPlaying

BlockyAreYouPlaying é um plugin para o servidor BlockyCRAFT que monitora a atividade dos jogadores, registrando periodicamente informações sobre os jogadores que estão online e autenticados através do `AuthPlugin`.

## Funcionalidades

- **Monitoramento Periódico:** Executa uma verificação em intervalos configuráveis para registrar a atividade dos jogadores.
- **Integração com AuthPlugin:** Verifica se um jogador está autenticado usando o método `isAuthenticated()` do `AuthPlugin` antes de registrar seus dados.
- **Registro de Dados:** Armazena informações detalhadas sobre os jogadores autenticados, incluindo UUID, nome de usuário, coordenadas (X, Y, Z), e rotação (Yaw, Pitch) em um banco de dados SQLite.
- **Configuração Flexível:** O intervalo de verificação pode ser facilmente ajustado no arquivo `config.yml`.

## Como Funciona

O plugin agenda uma tarefa que é executada repetidamente. A cada execução, a tarefa percorre a lista de todos os jogadores online no servidor. Para cada jogador, ele verifica se o jogador está autenticado. Se estiver, o plugin registra um novo conjunto de dados para esse jogador no banco de dados SQLite, capturando sua posição e estado atuais.

## Banco de Dados

O plugin cria e gerencia um banco de dados SQLite chamado `players.db` na pasta de dados do plugin. A tabela `are_you_playing` armazena os registros de atividade dos jogadores com a seguinte estrutura:

```sql
CREATE TABLE IF NOT EXISTS are_you_playing (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    uuid TEXT NOT NULL,
    username TEXT NOT NULL,
    x_pos REAL NOT NULL,
    y_pos REAL NOT NULL,
    z_pos REAL NOT NULL,
    yaw REAL NOT NULL,
    pitch REAL NOT NULL,
    created_at INTEGER NOT NULL
);
```

## Configuração

O arquivo `config.yml` permite definir o intervalo entre as verificações:

```yml
# Intervalo em segundos para verificar se os jogadores estao online
check_interval: 60
```

## Integração

- **AuthPlugin:** O BlockyAreYouPlaying depende do `AuthPlugin` para verificar o status de autenticação dos jogadores. Certifique-se de que o `AuthPlugin` esteja instalado e funcionando corretamente.

## Comandos e Permissões

Este plugin não adiciona novos comandos ou permissões para os jogadores. Sua operação é totalmente automática e executada em segundo plano.

## Reportar bugs ou requisitar features

Reporte bugs ou sugira novas funcionalidades na seção [Issues](https://github.com/andradecore/BlockyAreYouPlaying/issues) do projeto.

## Contato

- Discord: https://discord.gg/tthPMHrP
