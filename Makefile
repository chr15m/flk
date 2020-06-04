EXTRAMALS=alias-hacks.mal trivial.mal
LOCALMALS=math.clj reducers.clj
INLINES=$(foreach f,$(EXTRAMALS),mal/lib/$(f) ) $(foreach f,$(LOCALMALS),src/$(f) )
DEST?=flk
REVISION=$(shell git rev-parse HEAD | cut -b1-8)

$(DEST): mal/impls/bash/mal src/*.sh $(INLINES) Makefile
	cat $< | sed '/then exit/,$$d' > $@
	cat src/extras.sh >> $@
	printf 'read -d "" __FLECK__REPCAPTURE << __FLECK__INLINEMALFILE\n' >> $@
	cat $(INLINES) >> $@
	[ "$(INSERT)" = "" ] || grep -Ev '^\s*$|^\s*\#\!' $(INSERT) >> $@
	printf '\n__FLECK__INLINEMALFILE\nREP "(do $${__FLECK__REPCAPTURE})";\n' >> $@
	printf 'REP "(def! *fleck-revision* \\"$(REVISION)\\")"\n' >> $@
	if [ "$(NOREPL)" = "" ]; then cat src/file-repl.sh; fi >> $@
	chmod 755 $@

mal/impls/bash/mal:
	cd mal/impls/bash && make mal

test: flk
	./mal/runtest.py tests/str.mal ./flk
	./mal/runtest.py tests/math.mal ./flk
	./mal/runtest.py tests/env.mal ./flk
	./mal/runtest.py tests/loop.mal ./flk
	./mal/runtest.py tests/interop.mal ./flk

.PHONY: clean

clean:
	rm -f $(DEST)
