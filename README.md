# 🍅 Pomodoro App

Um aplicativo Android moderno e produtivo para gerenciar seu tempo utilizando a técnica Pomodoro. Desenvolvido com **Kotlin** e **Jetpack Compose** para uma experiência de usuário fluida e responsiva.

## ✨ Funcionalidades

- ⏱️ **Timer Pomodoro** - Ciclos configuráveis de trabalho e pausa
- 📋 **Gerenciamento de Tarefas** - Crie, edite e acompanhe suas tarefas
- 🔔 **Notificações com Som e Vibração** - Alertas sonoros e feedback tátil ao terminar sessões
- ⚙️ **Preferências Personalizáveis** - Ajuste duração das sessões e pausas conforme sua necessidade
- 💾 **Persistência de Dados** - Suas tarefas são salvas automaticamente

## 🛠️ Stack Tecnológico

### Frontend
- **Kotlin** - Linguagem moderna e type-safe para Android
- **Jetpack Compose** - Kit de ferramentas para UI declarativa
- **Material Design 3** - Design system moderno

### Arquitetura & Dados
- **MVVM Pattern** - Separação clara de responsabilidades
- **DataStore** - Armazenamento seguro e assíncrono de preferências
- **Coroutines** - Programação assíncrona eficiente
- **Kotlinx Serialization** - Serialização de dados estruturados

### Recursos Android
- **Navigation Compose** - Roteamento declarativo entre telas
- **Lifecycle** - Gerenciamento seguro do ciclo de vida
- **Material Components** - Componentes UI robustos

## 🏗️ Arquitetura

```
app/src/main/java/com/ikaroorg/pomodoro_app/
├── ui/
│   ├── screen/          # Telas da aplicação (Home, Settings)
│   ├── theme/           # Temas e estilos
│   └── components/      # Componentes reutilizáveis
├── viewmodel/           # ViewModels (HomeViewModel, SettingsViewModel)
├── navigation/          # Navegação entre telas
├── data/
│   ├── model/           # Modelos de dados (Task)
│   └── local/           # Gerenciamento local (DataStore)
└── MainActivity.kt      # Ponto de entrada
```

## 🚀 Como Iniciar

### Pré-requisitos
- Android Studio Koala ou mais recente
- Java 11+
- Android SDK 24+

### Instalação

1. Clone o repositório:
```bash
git clone https://github.com/kauas3228/pomodoro_app.git
cd pomodoro_app
```

2. Abra o projeto no Android Studio

3. Sincronize as dependências do Gradle:
```bash
./gradlew sync
```

4. Execute em um dispositivo ou emulador:
```bash
./gradlew installDebug
```

## 📱 Requisitos Mínimos

- **API Level**: 24 (Android 7.0)
- **Target SDK**: 36 (Android 15)
- **Compilação SDK**: 36

## 🎯 Destaques de Desenvolvimento

✅ **Clean Architecture** - Código modular e testável
✅ **Reactive Programming** - Coroutines para operações assíncronas
✅ **Modern UI** - Jetpack Compose para interfaces declarativas
✅ **Type Safety** - Kotlin eliminando nullpointers comuns
✅ **State Management** - ViewModel com padrão MVVM
✅ **Data Persistence** - DataStore para preferências seguras

## 📚 Dependências Principais

- `androidx.compose.ui:ui` - Framework Compose
- `androidx.navigation:navigation-compose` - Navegação
- `androidx.datastore:datastore-preferences` - Persistência
- `org.jetbrains.kotlinx:kotlinx-coroutines` - Concorrência
- `org.jetbrains.kotlinx:kotlinx-serialization` - Serialização
- `androidx.compose.material3:material3` - Material Design 3

## 🤝 Contribuindo

Contribuições são bem-vindas! Para grandes mudanças, abra uma issue primeiro para discutir suas propostas.
