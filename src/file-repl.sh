# load/run file from command line (then exit)
if [[ "${1}" ]]; then
    REP "(load-file \"${1}\")"
    exit 0
fi

# repl loop
REP "(println (str \"Fleck\"))"
while true; do
    READLINE "user> " || exit "$?"
    [[ "${r}" ]] && REP "${r}" && echo "${r}"
done
