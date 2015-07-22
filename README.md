## Development

To run the Figwheel development server, run:

```sh
lein do clean, run
```

The application will now be available at http://localhost:3000.

To start the Figwheel compiler, run the following command in a separate terminal:

```sh
lein figwheel
```

Figwheel will automatically push cljs changes to the browser.

If you're only doing client-side development then it's sufficient to simply run the Figwheel compiler and then browse to http://localhost:3449 once it starts up.

### Working files


The main file we want to work on is `src/cljs/kindling/core.cljs` (and maybe `src/clj/kindling/handler.clj`).
