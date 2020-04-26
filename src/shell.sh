_sh() {
  local args=""
  local cmd="${ANON["${1}"]}"; shift
  [ "${1}" != "" ] && args="${ANON["${1}"]}"; shift

  local cmdargs="${cmd}"
  for _arg in ${args}; do
    cmdargs="$cmdargs ${ANON["${_arg}"]}";
  done

  # See answer to "Bash: Assign output of pipe to a variable" by
  # [Stéphane Chazelas](https://unix.stackexchange.com/a/365228)
  local output=""
  { eval "${cmdargs}" > /dev/fd/3 && output=$(cat <&3); } 3<<< ''
  _string "${output%$'\n'}"
}

_fref "sh" _sh

