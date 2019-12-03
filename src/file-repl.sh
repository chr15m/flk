( return 0 2>/dev/null ) && _fleck_sourced=1 || _fleck_sourced=0

if [ "${_fleck_sourced}" = "0" ]
then
  # load/run file from command line (then exit)
  if [[ "${1}" ]]; then
      REP "(load-file-without-hashbang \"${1}\")"
      [ "${r}" = "nil" ] && exit 0 || { echo "${r}"; exit 127; };
  fi

  # repl loop
  [ -t 0 ] && REP "(println (str \"Fleck\"))"
  while true; do
      READLINE "user> " || exit "$?"
      [[ "${r}" ]] && REP "${r}" && ( [ -t 0 ] && echo "${r}" )
  done
fi
