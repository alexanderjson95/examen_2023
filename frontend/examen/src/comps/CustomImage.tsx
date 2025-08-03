type ImageProp = {
  src: string;
  alt: string;
  width?: string | number;
  height?: string | number;
  style?: React.CSSProperties;
  className?: string;
}
//+ placeholder 

export const CustomImage = ({src,alt,width,height,style,className}: ImageProp) => {
    return (
        <img
         src={src}
         alt= {alt}
         width={width}
         height={height}
         style={style}
         className={className}
        />
    )
}
