type InputProp = {
    onChange: (e: React.ChangeEvent<HTMLInputElement>) => void;
    value: string;
    required?: boolean;
    id?: string;
}


export const CustomInput = ({onChange, value, required}: InputProp) => {
    return (
            <input value={value} onChange={onChange} required = {required}/>
    )
}