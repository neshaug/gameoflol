# gameoflol

John Conway's Game of Life in ClojureScript with the core algorithm from O'Reilly's Clojure Programming

## Usage

```bash
 lein ring server-headless 3000
 lein trampoline cljsbuild repl-launch chrome http://localhost:3000/index.html
 
 # add a glider from the browser repl
 (ns gameoflol.core)
 (tick surface #{[2 0] [2 1] [2 2] [1 2] [0 1]})
```