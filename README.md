# EP Solar System Explorer

This project is an attempt to bring an interactive 3D map to the [Eclipse Phase][ep] roleplaying game world.

The project makes use of WebGL via [three.js][three] and [Scala.js][scalajs] to render everything in a [HTML5][html5] capable browser.


[ep]: http://eclipsephase.com/
[three]: https://threejs.org/
[scalajs]: https://www.scala-js.org/
[html5]: https://www.w3.org/TR/html5/

## Status

Currently in alpha stage with an experimental deployment at <http://epexplorer.lkroll.com/>.

## Dependencies

JavaScript dependencies, fonts, and icons are installed with Bun.

## Development

Install the JavaScript dependencies and start the Vite development server:

```sh
bun install
bun run dev
```

Open the local URL printed by Vite. The `dev` command builds the Scala.js application before starting the server. After changing Scala source, run `sbt -batch fastLinkJS` again and refresh the page.

Run the Scala.js unit tests with `sbt -batch test` (incremental in sbt 2) or
`sbt -batch testFull` (all suites).

Run the CI checks locally:

```sh
bun install --frozen-lockfile
sbt -batch testFull
bun run build
```

Check Scala style without changing sources:

```sh
sbt -batch 'scalafixAll --check'
```

To rebuild and inspect the production site locally:

```sh
bun run preview
```

The `preview` command builds the production site in `dist/` before serving it. Run `bun run build` to build without starting the preview server.

## Static release archive

Build and check an archive locally:

```sh
nu scripts/build-release.nu
cd release
shasum -a 256 --check ep-explorer-*.tar.gz.sha256
tar -tzf ep-explorer-*.tar.gz
```

Run the [Release workflow](.github/workflows/release.yml) manually for a downloadable Actions artefact. Push a `vMAJOR.MINOR.PATCH` tag matching `mapviewer.sbt` to publish a GitHub release.

## TODO

### Definitely

### Nice to have
- Travel Calc
- Add more moons if necessary
- Region markers (e.g. Trojans, Greeks)
- LOD
- Object filters
- Some hints at geography (at least for the planets)
- Space elevators and transportation networks

## Licenses
The material is based on *Eclipse Phase* by [Posthuman Studios][ep] and is published under Creative Commons (BY-NC-SA) 3.0 (license)[https://creativecommons.org/licenses/by-nc-sa/3.0/] as is the original material.
