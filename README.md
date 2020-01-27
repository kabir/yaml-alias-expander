# yaml-alias-expander

This is a simple processor which takes a YAML file with anchors and aliases is input and outputs a new YAML file with the aliases 'expanded'.

## Usage
Download the latest [release](https://github.com/kabir/yaml-alias-expander/releases).

Then run:
```
java -jar /path/to/yaml-alias-expander-x.y.z.jar /path/to/input.yml /path/to/output.yml
```

In the input file, keys that contain an anchor need to start with `x-`. An input file 
with anchors and aliases that looks like:
```
x-defaults: &default-child-contents
  entries:
    - name: echo
      run: echo ${MY_VAR}
      # 'on' needs to be quoted, or the processor will translate it to 'true'
      "on": production
x-more: &more
      more: and more

name: My example
children:
  one:
    env:
      MY_VAR: 1
      <<: *default-child-contents
      <<: *more
  two:
    env:
      MY_VAR: 2
      <<: *default-child-contents
      <<: *more
  three:
    env:
      MY_VAR: 3
      <<: *default-child-contents
      <<: *more
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
      more: and more
  two:
    env:
      MY_VAR: 2
      entries:
        - name: echo
          run: echo ${MY_VAR}
          "on": production
      more: and more
  three:
    env:
      MY_VAR: 3
      entries:
        - name: echo
          run: echo ${MY_VAR}
          "on": production
      more: and more

```
