EXTRAMALS=alias-hacks.mal trivial.mal
LOCALMALS=math.clj
INLINES=$(foreach f,$(EXTRAMALS),mal/lib/$(f) ) $(foreach f,$(LOCALMALS),src/$(f) )
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

test: flk
	./mal/runtest.py tests/str.mal ./flk
	./mal/runtest.py tests/math.mal ./flk
	./mal/runtest.py tests/env.mal ./flk

.PHONY: clean

clean:
	rm -f $(DEST)
