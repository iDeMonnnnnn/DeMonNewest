src="app/src/main/res/drawable-xxxhdpi/logo.png"

filename_no_ext="${src%.*}"
echo "$filename_no_ext"
dst="${filename_no_ext}_round.png"

# 获取宽高
read w h < <(identify -format "%w %h" "$src")
# 取较小值为圆直径
if [ "$w" -lt "$h" ]; then
  size=$w
else
  size=$h
fi

# 生成圆形图片
convert "$src" -resize "${size}x${size}^" \
  -gravity center -crop "${size}x${size}+0+0" +repage \
  -background none -extent "${size}x${size}" \
  \( +clone -alpha extract \
    -draw "fill black polygon 0,0 0,${size} ${size},0 fill white circle $((size/2)),$((size/2)) $((size/2)),0" \
    \( +clone -flip \) -compose Multiply -composite \
    \( +clone -flop \) -compose Multiply -composite \
  \) -alpha off -compose CopyOpacity -composite \
  "$dst"