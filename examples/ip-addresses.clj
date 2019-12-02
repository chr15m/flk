(let [cmd "ip addr | grep -oE '\b([0-9]{1,3}\.){3}[0-9]{1,3}\b'"
      ips (str-split (sh* cmd) "\n")]
  (println (pr-str {:ips ips})))
