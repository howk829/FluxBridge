"use client"
import { AreaChart, Area, XAxis, YAxis, CartesianGrid, Legend, ResponsiveContainer, Label  } from "recharts"
import {
  Table,
  TableBody,
  TableCaption,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table"

import {
  Accordion,
  AccordionContent,
  AccordionItem,
  AccordionTrigger,
} from "@/components/ui/accordion"

import { Share2, LayoutGrid, Settings, RefreshCcw, Clock } from "lucide-react"

import FluxSelect from "@/components/flux/select"
import { 
  healthcheckBoards, 
  systemBoards, 
  consumerSubscribedTopics,
  refreshRates,
  periods
} from "./utils/mock"

export default function Home() {
  return (
    <>
      <nav id="main-nav" className="h-16 flex justify-between p-8">
        <div className="flex justify-between items-center w-1/6">
          <button><LayoutGrid /></button>
          <p>Flux</p>
          <p> / </p>
          <p>Overview</p>
          <button><Share2 /></button>
        </div>
        <div className="flex items-center gap-4">
          <button><Settings /></button>
          <button><Clock /></button>
          <FluxSelect placeholder="Period" values={periods}/>
          <button><RefreshCcw /></button>
          <FluxSelect placeholder="Refresh rate" values={refreshRates}/>
        </div>
      </nav>

      <nav id="sub-nav" className="flex justify-start gap-4 m-4">
        <p className="text-blue-400 bg-gray-800 rounded-lg p-2">datasource</p>
        <FluxSelect placeholder="Flux"/>

        <p className="text-blue-400 bg-gray-800 rounded-lg p-2">Consumer Id</p>
        <FluxSelect placeholder="All"/>

        <p className="text-blue-400 bg-gray-800 rounded-lg p-2">Percentile</p>
        <FluxSelect placeholder="All"/>

        <p className="text-blue-400 bg-gray-800 rounded-lg p-2">Topic</p>
        <FluxSelect placeholder="All"/>
      </nav>

      <main id="dashboard">
        <Accordion type="multiple" className="w-full px-4" defaultValue={["healthcheck", "system","subscription"]}>
          <AccordionItem value="healthcheck">
            <AccordionTrigger>Healthcheck</AccordionTrigger>
            <AccordionContent>
              <div className="grid grid-flow-row gap-2 my-4">
                {
                  healthcheckBoards.map((board, index) => 
                    (
                      <div key={`healthcheck-board-${index}`} className="text-center bg-gray-900 rounded-xl min-w-[160px]">
                        <h1 className="mt-4">{board.title}</h1>
                        <p className="text-6xl font-medium text-green-700 my-8">{board.count}</p>
                      </div>
                    )
                  )
                }
              </div>
            </AccordionContent>
          </AccordionItem>
          <AccordionItem value="system">
            <AccordionTrigger>System</AccordionTrigger>
            <AccordionContent>
              <div className="grid grid-cols-3 gap-2">
                {
                  systemBoards.map((board, index) => 
                    (
                      <div key={`system-board-${index}`} className="text-center">
                        <p>{board.title}</p>
                        <ResponsiveContainer width="100%" height={300}>
                          <AreaChart
                            data={board.data}
                            margin={{
                              top: 5,
                              right: 30,
                              left: 20,
                              bottom: 5,
                            }}
                            >
                              <CartesianGrid />
                              <XAxis dataKey="timestamp" />
                              <YAxis label={{value: board.yaxis, angle: -90, position: 'insideLeft'}}/>
                              <Legend />
                              <Area type="monotone" dataKey="machine1" stroke="#8884d8" isAnimationActive={false} />
                              <Area type="monotone" dataKey="machine2" stroke="#82ca9d" isAnimationActive={false} />
                            </AreaChart>
                        </ResponsiveContainer>
                      </div>
                    )
                  )
                }
              </div>
            </AccordionContent>
          </AccordionItem>
          <AccordionItem value="subscription">
            <AccordionTrigger>Consumer Subscription</AccordionTrigger>
            <AccordionContent>
              <Table>
                <TableCaption>A list of Consumer subscribed topic.</TableCaption>
                <TableHeader>
                  <TableRow>
                    <TableHead className="w-[100px]">Consumer</TableHead>
                    <TableHead>Status</TableHead>
                    <TableHead>Topic</TableHead>
                  </TableRow>
                </TableHeader>
                <TableBody>
                  {
                    consumerSubscribedTopics.map(consumer => (
                      <TableRow key={`${consumer.name}-topic`}>
                        <TableCell className="font-medium">{consumer.name}</TableCell>
                        <TableCell>{consumer.status}</TableCell>
                        <TableCell className="flex gap-4">
                          {
                            consumer.topic.map(topic => (
                                <p key={`${consumer.name}-topic-${topic}`}>
                                  {topic}
                                </p>
                            ))
                          }
                        </TableCell>
                      </TableRow>
                    ))
                  }
                </TableBody>
              </Table>
            </AccordionContent>
          </AccordionItem>
        </Accordion>
      </main>
    </>
  )
}
