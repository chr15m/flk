# load/run file from command line (then exit)
if [[ "${1}" ]]; then
    REP "(load-file \"${1}\")"
    [ "${r}" = "nil" ] && exit 0 || { echo "${r}"; exit 127; };
fi


# repl loop
REP "(println (str \"Fleck\"))"
while true; do
    READLINE "user> " || exit "$?"
    [[ "${r}" ]] && REP "${r}" && echo "${r}"
done
