# Cake Solutions Limited - Carl Pulley

## Concordance Example

The application implementation is split between 5 files:
  * `Main.scala`          this is the main application and binds all components together
  * `EnglishParser.scala` this file uses the [Epic](https://github.com/dlwh/epic) NLP parser to generate sentence and
  word lists
  * `ResultView.scala`    this file implements functions for generating a view of the concordance data and for outputing
  that data to a file
  * `Worker.scala`        this file holds the main worker actor that is responsible for collating word and sentence
  frequency information using sorted data structures.

### Assumptions:

  * concordance data is to be presented in an alphabetical sorted order
    * this is implied by the explanatory phrase "an alphabetical list of all word occurrences" and by the mock table that
    is presented.
  * actors do not fail
    * an unrealistic assumption in general.

### Design Notes

A messaging pattern has been used here to help separate the concerns of input data representation and how it is to be
processed.

Akka actors were introduced to provide a component based solution and to implement this messaging pattern.

Should it be necessary to ensure that the stored concordance data is resilient to application restarts, then it is also
viable to engineer the `Worker` actor with Akka persistence properties. Additionally, should we wish to scale, then we
may replace the single `Worker` actor with a system of co-ordinated actors.

## Build Instructions

First ensure that Java and [sbt](http://www.scala-sbt.org/download.html) are installed.

To compile:

    sbt compile

To run tests:

    sbt test

To run actual application on an input test file (e.g. kjbible.txt) and save sorted results (to output.txt):

    sbt "run kjbible.txt concordance.txt"

Whilst not fully configured, code is in place should it be necessary to build deployments (e.g. binary tar balls, Debian
packages, RPM packages or Docker application containers). For example, to build a deployment zip file:

    sbt stage
    sbt universal:packageBin
    # Zip artefact created: target/universal/bridgewater-test-0.1.zip
