_str_replace () {
  local s="${ANON["${1}"]}"; shift
  local f="${ANON["${1}"]}"; shift
  local x="${ANON["${1}"]}"; shift
  _string "${s//${f}/${x}}"
}

_fref "str-replace" _str_replace
