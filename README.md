# jsonconvert

A small utility application written in Kotlin to change the case of attributes within a Json file to either lower case or upper case.

## License

This software is licensed under the MIT license. Full details are in the `LICENSE.md` file.

## Build

The software can be built into an uber jar with all its dependencies included in the jar. To build this, execute:

``` bash
./gradlew uberJar
```

## Running

``` bash
java -jar jsonconvert.jar <options> input.json output.json
```

The command line options available are:

* `-l` convert the input file Json attribute names to lower case
* `-u` convert the input file Json attribute name sto upper case
* `-h` display help information

If no option is specified, the default is to covert to lower case.

### Help

``` bash
Usage: jsonconvert [<options>] <input> <output>                                                                                                

  Convert attribute names in a Json file to either lower or upper case. If a conversion to upper or lower case is not specified, the input file
  will default to having attributes converted to lower case.

  (c) David Salter 2024

Options:
  -l, --lower  Convert attribute names to lower case. (The default option)
  -u, --upper  Convert attribute names to upper case.
  -h, --help   Show this message and exit

Arguments:
  <input>   Read Json to convert from this file
  <output>  Write converted Json to this file
```