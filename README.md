# Projeto: Sistema de Gerenciamento de Veículos (Android Nativo)

Desenvolvi um aplicativo de gerenciamento de entradas e saídas de veículos para controle de acesso (simulando um contexto de garagem ou segurança), utilizando Kotlin e a arquitetura MVVM (Model-View-ViewModel) para garantir código limpo, testável e de fácil manutenção.

## Destaques Técnicos e Funcionais:

*   **Persistência de Dados:** Implementação completa do Room Persistence Library (baseado em SQLite) para armazenamento local eficiente dos registros de veículos (`VehicleEntity`), incluindo a definição de um DAO (`VehicleDao`) com operações CRUD (Create, Read, Update, Delete) e métodos de busca customizados.
*   **Gerenciamento de Estado:** Utilização de Coroutines e Lifecycle-aware components (como `lifecycleScope`) para gerenciar operações assíncronas de banco de dados, garantindo que a UI permaneça responsiva e evitando vazamentos de memória.
*   **Interface e Experiência do Usuário (UI/UX):**
    *   Desenvolvimento de telas de autenticação (`SignInActivity`, `SignUpActivity`) e um menu principal (`MenuActivity`) com navegação clara.
    *   Implementação de uma tela de registro (`AddEntryActivity`) com validação de campos obrigatórios e lógica para evitar a duplicação de registros (verificação por nome e placa).
    *   Criação de uma tela de listagem e gerenciamento (`VehicleListActivity`) com `RecyclerView` e um `VehicleAdapter` customizado, permitindo a visualização, edição e exclusão de registros.
    *   Funcionalidade de Busca e Filtragem Dinâmica utilizando Chips (Material Design) para refinar a pesquisa por placa, nome, motivo ou data/hora.
*   **Arquitetura:** Estruturação do projeto em pacotes claros (`data`, `model`, `ui`, `viewmodel`), seguindo as melhores práticas de desenvolvimento Android.

