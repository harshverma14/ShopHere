# SwingShop Pro (Dark Theme)
Modernized Swing app with FlatLaf dark theme, sidebar navigation, and fast page switching.

## Quick start (WSL)
```bash
# 1) Import schema (once)
mysql -u shopuser -p swingshop < src/main/resources/schema.sql

# 2) Build
mvn clean package

# 3) Run
java -cp target/swingshop-pro-1.0.0.jar:target/lib/* com.swingshop.Main
```
