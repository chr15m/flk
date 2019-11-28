EXTRAMALS=alias-hacks.mal pprint.mal
INLINES=$(foreach f, $(EXTRAMALS), mal/lib/$(f))

idm: mal/bash/mal src/*.sh $(INLINES) Makefile
	cat $< | sed '/then exit/,$$d' > $@
	for f in "$(INLINES)"; do ( echo 'read -d "" _REPCAPTURE <<INLINEMALFILE'; cat mal/lib/$$f; echo 'INLINEMALFILE\nREP "(do $${_REPCAPTURE})";\n'; ) >> $@; done
	cat src/file-repl.sh >> $@
	chmod 755 $@

mal/bash/mal:
	cd mal/bash && make mal

.PHONY: clean

clean:
	rm -f idm
