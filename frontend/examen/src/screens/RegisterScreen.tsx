import { CustomButton } from '../comps/CustomButton'
import { CustomForm } from '../comps/CustomForm'
import { CustomInput } from '../comps/CustomInput'
    
    function RegisterScreen() {

        function Register(){}
    
      function input(e: React.ChangeEvent<HTMLInputElement>){
        console.log(e) //placeholder sålänge
      }
    
      return (
        <>
          <CustomForm 
            onSubmit={Register}>
            <>
            <CustomInput onChange={input} value='username'/>
            <CustomInput onChange={input} value='password'/>
            <CustomInput onChange={input} value='email'/>
            <CustomInput onChange={input} value='age'/>
            <CustomInput onChange={input} value='city'/>
            <CustomInput onChange={input} value='past experience (role)'/>
            <CustomInput onChange={input} value='years/month of experience (at role)'/>
            </>
            </CustomForm>
          <CustomButton label='LOG IN' />
          <CustomButton label='REGISTER' />
        </>
      )
    }
    
    export default RegisterScreen
    
