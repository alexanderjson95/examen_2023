import { useEffect, useState } from 'react'
import reactLogo from './assets/react.svg'
import viteLogo from '/vite.svg'
import './App.css'
import { LoginScreen } from './screens/LoginScreen'
import { ProjectScreen } from './screens/ProjectScreen'
import { CustomButton } from './comps/CustomButton'



function App() {

  const data = [
    {id: 13, projectName: 'apa', status: true},
    {id: 2, projectName: 'elefant', status: false},
  ]
  const [isChecked, setIsChecked] = useState(false);
  const [allData, setAllData] = useState(data);

  const sortBy = () => {
    console.log('filter: ' + isChecked)
    return data.filter(project => project.status === true)
  }

  
  useEffect(() => {
    if(isChecked) {
      const filter = sortBy
      setAllData(filter)
    } else {
      setAllData(data)
    }
  })


  return (
    <>
    <table>
      <thead>
        <tr>
          <th>ID</th>
          <th onClick={sortBy}>Name</th>
        </tr>
      </thead>
      <tbody>
        {allData.map((project) => (
          <tr key={project.id}> {project.id}
          <td data-id = {project.id} onClick={(e) => {const id = e.currentTarget.dataset.id; console.log(id)}}>
          {project.projectName}
          </td>
          
          <td data-id = {project.id} onClick={(e) => {const id = e.currentTarget.dataset.id; console.log(id)}}>{project.status}</td>

          </tr>
        ))}
      </tbody>
    </table>
    <input></input>
    <input type='checkbox' name='hireFilter'  checked={isChecked} onChange={() => {setIsChecked(!isChecked); sortBy('hiring')}}></input>

    <CustomButton label="SÖK"/>
    
    </>
  )
}

export default App
