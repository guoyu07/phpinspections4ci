What is this?
---

PHP-plugin for IDEA CE based on Consulo IDE (https://consulo.io/) PHP plugin (https://github.com/consulo/consulo-php).
It's for demonstration purposes only, please don't use for development - support is not provided currently.

Apparently the plgin has longer story:
- http://idea-php.sourceforge.net
- https://plugins.jetbrains.com/plugin/1861-php-for-idea-8

Current status
---

- Lexer/Parser needs work:
- We ported sample Php Inspections (EA Extended) inspections;
- Indexing, navigation all ets has been disabled - only syntax highlighting and inspections are there;
- Public announcement is coming next weeks;

Demo
---

- Get IDEA CE and install plugin from binary in the repo master;
- Create PHP-file and insert this text there;
```php
<?php
    /* -- ArrayPushMissUseInspector -- */
    array_push($arr, '');

    /* -- DateUsageInspector -- */
    date('...', time());

    /* -- MktimeUsageInspector -- */
    mktime();
    mktime(0, 0, 0, 0, 0, 0, -1);
```
- See already familiar messages got reported.
