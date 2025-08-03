type FormProp = {
  onSubmit: (e: React.FormEvent<HTMLFormElement>) => void;
  children: React.ReactNode;
}


export const CustomForm = ({children, onSubmit}: FormProp) => {
    return (
        <form onSubmit={onSubmit}>
            {children}
        </form>
    )
}