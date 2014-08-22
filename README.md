picasso-helper
==============

Override default downloader and enable you to setting file cache'S max age and max size manually.

## Usage

```java
PicassoHelper.with(this)
    .load(url)
    .placeholder(R.drawable.placeholder)
    .error(R.drawable.error)
    .fit()
    .into(view);
```