type ButtonProp = {
    label: string;
    onClick?: () => void;
    disabled?: boolean;
    type?: React.ButtonHTMLAttributes<HTMLButtonElement>["type"];
}

export const CustomButton = ({type, label, onClick, disabled}: ButtonProp) =>{
    return (
        <button onClick={onClick} disabled={disabled} type={type}>
            {label}
        </button>
    )
}
