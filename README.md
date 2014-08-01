°Bx &ndash; Sugar Cubes for Scala Development
=============================================

°Bx (brix) consists of a set of Scala libraries that make Web application development faster and easier.

http://dev.gokillo.com

## Installing

°Bx is available in the [Gok!llo Community Repository](http://repo.gokillo.com). Just edit `build.sbt` and add the required resolvers and dependencies:

```scala
resolvers ++= Seq(
  Resolver.url("Gok!llo repository releases", url("http://repo.gokillo.com/releases"))(Resolver.ivyStylePatterns),
  Resolver.url("Gok!llo repository snapshots", url("http://repo.gokillo.com/snapshots"))(Resolver.ivyStylePatterns)
)

libraryDependencies ++= Seq(
  "com.gokillo" %% "brix-corelib" % "0.1.0-SNAPSHOT"
)
```
## Getting started

Coming soon...

## Licence

This software is licensed under the Apache 2 license, quoted below.

Copyright 2014 [Gok!llo](http://gokillo.com).

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this project except in compliance with the License. You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0.

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an required SIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
