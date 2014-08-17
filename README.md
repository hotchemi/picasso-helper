picasso-helper
==============

**This product is under construction...**

Override default downloader and enable you to setting file cache'S max age and max size manually.

## Usage

```java
PicassoHelper.getInstance(getApplicationContext())
    .load(url)
    .placeholder(R.drawable.placeholder)
    .error(R.drawable.error)
    .fit()
    .into(view);
```