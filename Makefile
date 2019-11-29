EXTRAMALS=alias-hacks.mal pprint.mal
INLINES=$(foreach f,$(EXTRAMALS),mal/lib/$(f) )
DEST?=flk

$(DEST): mal/bash/mal src/*.sh $(INLINES) Makefile
	cat $< | sed '/then exit/,$$d' > $@
	cat src/extras.sh >> $@
	echo 'read -d "" _REPCAPTURE <<INLINEMALFILE' >> $@
	cat $(INLINES) >> $@
	[ "$(INSERT)" = "" ] || cat $(INSERT) >> $@
	echo 'INLINEMALFILE\nREP "(do $${_REPCAPTURE})";\n' >> $@
	if [ "$(NOREPL)" = "" ]; then cat src/file-repl.sh; fi >> $@
	chmod 755 $@

mal/bash/mal:
	cd mal/bash && make mal

.PHONY: clean

clean:
	rm -f $(DEST)
