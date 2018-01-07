# EP Solar System Explorer

This project is an attempt to bring an interactive 3D map to the [Eclipse Phase][ep] roleplaying game world.

The project makes use of WebGL via [three.js][three] and [Scala.js][scalajs] to render everything in a [HTML5][html5] capable browser.


[ep]: http://eclipsephase.com/
[three]: https://threejs.org/
[scalajs]: https://www.scala-js.org/
[html5]: https://www.w3.org/TR/html5/

## Status

Currently in alpha stage with an experimental deployment at <http://epexplorer.lars-kroll.com/>.

## Dependencies

This project depends on a custom build of the [threejs-facade](https://github.com/antonkulaga/threejs-facade) for Scala which can be found at [Bathtor/threejs-facade](https://github.com/Bathtor/threejs-facade).

## Development

First checkout, build, and publish the dependencies (see above) to your local ivy repository.

To build and run the project, check it out from git, cd into the project folder and run `sbt`. In sbt run `fastOptJS` to build the project. You can access the development version through your browser at <http://localhost:12345/target/scala-2.12/classes/WEB-INF/index-dev.html> (you might have to change the entry for `localUrl` in `mapviewer.sbt` an address you may actually bind on).

## TODO

### Definitely
Add habitats and settlements for and around
- Saturn
- Uranus
- Neptune
- Pluto
- Eris
- Kuiper Belt

### Nice to have
- Add more moons if necessary
- Region markers (e.g. Trojans, Greeks)
- LOD
- Object filters
- Some hints at geography (at least for the planets)
- Space elevators and transportation networks
