DIR="$( cd "$( dirname "$0" )" && pwd )"
OUTPUT="${DIR}/output"

mkdir $OUTPUT

echo "Copying files..."

INDEX=""
EXCLUDED=(index.html error.html LICENSE.txt build.sh output robots.txt)

for file in $DIR/*; do
  filename="${file##*/}"
  if [[ !(${EXCLUDED[*]} =~ $filename) ]]; then
    echo "Copying $file as $filename"
    cp $file $OUTPUT/$filename
    INDEX="$INDEX <a href=\"${filename}\">${filename}</a>"
  fi
done;

echo "Copying hidden files..."
cp "$DIR/error.html" "$OUTPUT/error.html"
cp "$DIR/LICENSE.txt" "$OUTPUT/LICENSE.txt"
cp "$DIR/robots.txt" "$OUTPUT/robots.txt"

echo "Outputting index..."
html=`cat $DIR/index.html`
htmlFormatted=${html/\$CONTENT\$/$INDEX}

echo "$htmlFormatted" > $OUTPUT/index.html

echo "Done!"
