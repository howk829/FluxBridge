import {
   Select,
   SelectContent,
   SelectItem,
   SelectTrigger,
   SelectValue,
 } from "@/components/ui/select"

type FluxSelectProps = {
   placeholder: string,
   values?:
      {
         value: string,
         label: string
      }[],
   width?: number
}

 export default function FluxSelect({ placeholder, values = [{ value: "", label: placeholder }], width = 160 }: FluxSelectProps) {
   return (
      <Select defaultValue={values[0].value}>
         <SelectTrigger className={`w-[${width}px]`}>
            <SelectValue placeholder={placeholder} />
         </SelectTrigger>
         <SelectContent>
            {
               values.map(value => (
                  <SelectItem key={`${placeholder}-${value.label}`} value={value.value}>{value.label}</SelectItem>
               ))
            }
         </SelectContent>
      </Select>
   )
 }