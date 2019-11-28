_str_replace () {
  local s="${ANON["${1}"]}"; shift
  local f="${ANON["${1}"]}"; shift
  local x="${ANON["${1}"]}"; shift
  _string "${s//${f}/${x}}"
}

_fref "str-replace" _str_replace

_str_split() {
  local var="${ANON["${1}"]}"; shift
  local ifs="${ANON["${1}"]}"; shift
  _list
  local newlist="${r}"

  while IFS="${ifs}" read -ra _str_split_arr; do
        for _str_split_itm in "${_str_split_arr[@]}"; do
          _string "${_str_split_itm}";
          _conj! "${newlist}" "${r}";
        done
  done <<< "${var}"
  r="${newlist}"
}

_fref "str-split" _str_split
