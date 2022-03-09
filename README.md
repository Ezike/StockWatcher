### Please check the media folder for a screen recording of the app in action

## Design

In approaching this project, I decided to use the tinder scarlet websocket library after considering
hooking directly to the java websocket class. Scarlet comes with really nice features like lifecycle
awareness (pauses the websocket when the app is paused and resumes when it comes in focus), and also
represent websocket events in a type safe way.

One of the challenges in this implementation was having a robust error handling strategy. I added an
event loop `ElapsedTimeTicker` that listens for connection failure or close events in the socket.
When it receives this event, then it starts emitting the timestamp when the last data was received
from the socket. This allows the user to still have some data on the screen, and also be aware that
it is stale data.

Handling backpressure was also a concern since the websocket emits so many events in very quick
intervals. So I made use of flow operators like `conflate`, which runs the emitter in a separate
coroutine, and emits only the last item since we do not really care about the old values. The ui
also consumes the data in 3 second intervals so that the view is not refreshed rapidly.

## Architecture

The application follows clean architecture because of the benefits it brings to software which
includes scalability, maintainability and testability. It enforces separation of concerns and
dependency inversion, where higher and lower level layers all depend on abstractions. We have 3
modules:

- Remote - a module that encapsulates the socket logic and exposes a contract `StockRemote` that
  other modules can consume.
- lib_stock_price - depends on the remote module to receive the socket events and defines the
  business logic and domain objects. The user interface is in the app module but could be in it's
  own module instead

For dependency injection and asynchronous programming, the project uses Dagger Hilt and Coroutines
with Flow. Dagger Hilt is a fine abstraction over the vanilla dagger boilerplate, and is easy to
setup. Coroutines and Flow brings kotlin's expressibility and conciseness to asynchronous
programming, along with a fine suite of operators that make it a robust solution.

#### Domain

The domain layer contains the app business logic. It defines contracts for data operations and
domain models to be used in the app. All other layers have their own representation of these domain
models, and Mapper classes (or adapters) are used to transform the domain models to each layer's
domain model representation. Usecases which represent a single unit of business logic are also
defined in the domain layer, and are consumed by the presentation layer. Writing mappers and models
can take a lot of effort and result in boilerplate, but they make the codebase much more
maintainable and robust by separating concerns.

## Testing

The app contains unit tests in all the modules. You can run all tests by executing the gradle
task `runTests`

#### Improvements

Caching the last emitted response would have been a really good feature to add. This way, once the
socket is closed or fails, we cache the last emitted response and its timestamp. Then when the app
is reopened, the last response and the time when it was stored is shown on the ui so the user knows
that it is stale data. It avoids leaving the ui completely blank because there's no internet data.

The gradle script uses Kotlin Gradle DSL which brings Kotlin's rich language features to gradle
configuration. The project also uses ktlint to enforce adherence to the official kotlin code style.