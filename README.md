# yaml-alias-expander

This is a simple processor which takes a YAML file with anchors and aliases is input and outputs a new YAML file with the aliases 'expanded'.

## Usage
Download the latest [release](https://github.com/kabir/yaml-alias-expander/releases).

Then run:
```
java -jar /path/to/yaml-alias-expander-x.y.z.jar /path/to/input.yml /path/to/output.yml
```

An input file with aliases that looks like:
```
defaults: &default-child-contents
  entries:
    - name: echo
      run: echo ${MY_VAR}
      # 'on' needs to be quoted, or the processor will translate it to 'true'
      "on": production

name: My example
children:
  one:
    env:
      MY_VAR: 1
      <<: *default-child-contents
  two:
    env:
      MY_VAR: 2
      <<: *default-child-contents
  three:
    env:
      MY_VAR: 3
      <<: *default-child-contents
```
will be 'expanded' to:
```
name: My example
children:
  one:
    env:
      MY_VAR: 1
      entries:
        - name: echo
          run: echo ${MY_VAR}
          "on": production
  two:
    env:
      MY_VAR: 2
      entries:
        - name: echo
          run: echo ${MY_VAR}
          "on": production
  three:
    env:
      MY_VAR: 3
      entries:
        - name: echo
          run: echo ${MY_VAR}
          "on": production
```
