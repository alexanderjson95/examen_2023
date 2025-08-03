import { useEffect } from "react";

type ErrorProp = {
    message?: string;
    error: Error;
}


export const CustomError = ({message, error}: ErrorProp) => {
    useEffect(() => {
        console.log({ error });
    }, [error]);
    return (
        <>
            <p className="error_message">{message || 'Unknown error occured'}</p>
            <pre className="error_message">{error.message}</pre>
            <pre className="error_message">{error.stack}</pre>
        </>
    )
}