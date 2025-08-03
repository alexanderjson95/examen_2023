import { useState } from 'react'
import reactLogo from './assets/react.svg'
import viteLogo from '/vite.svg'
import './App.css'

import { CustomButton } from './comps/CustomButton'
import { CustomForm } from './comps/CustomForm'
import { CustomInput } from './comps/CustomInput'

function App() {
  const [count, setCount] = useState(0)
  function Auth(){}

  function input(e: React.ChangeEvent<HTMLInputElement>){
    console.log(e) //placeholder sålänge
  }

  return (
    <>
      <CustomForm 
        onSubmit={Auth} children={
        <>
        <CustomInput onChange={input} value='username'/>
        <CustomInput onChange={input} value='password'/>
        </>}      
        />

      
      <CustomButton label='LOG IN' />
      <CustomButton label='REGISTER' />
    </>
  )
}

export default App
