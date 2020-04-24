EXTRAMALS=alias-hacks.mal trivial.mal
LOCALMALS=math.clj reducers.clj
INLINES=$(foreach f,$(EXTRAMALS),mal/lib/$(f) ) $(foreach f,$(LOCALMALS),src/$(f) )
DEST?=flk

$(DEST): mal/bash/mal src/*.sh $(INLINES) Makefile
	cat $< | sed '/then exit/,$$d' > $@
	cat src/extras.sh >> $@
	printf 'read -d "" __FLECK__REPCAPTURE << __FLECK__INLINEMALFILE\n' >> $@
	cat $(INLINES) >> $@
	[ "$(INSERT)" = "" ] || cat $(INSERT) >> $@
	printf '\n__FLECK__INLINEMALFILE\nREP "(do $${__FLECK__REPCAPTURE})";\n' >> $@
	if [ "$(NOREPL)" = "" ]; then cat src/file-repl.sh; fi >> $@
	chmod 755 $@

mal/bash/mal:
	cd mal/bash && make mal

test: flk
	./mal/runtest.py tests/str.mal ./flk
	./mal/runtest.py tests/math.mal ./flk
	./mal/runtest.py tests/env.mal ./flk
	./mal/runtest.py tests/loop.mal ./flk
	./mal/runtest.py tests/interop.mal ./flk

.PHONY: clean

clean:
	rm -f $(DEST)
