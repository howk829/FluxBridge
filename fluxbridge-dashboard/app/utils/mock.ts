type THealthcheckBoard = {
   title: string,
   count: number,
 }
 
 export const healthcheckBoards: THealthcheckBoard[] = [
   {
     title: "Active Controllers",
     count: 1,
   },
   {
     title: "Brokers Online",
     count: 2,
   },
   {
     title: "Unclean Leader Elections Rate",
     count: 0,
   },
   {
     title: "Preferred Replica Imbalance",
     count: 0,
   },
   {
     title: "Offline Partitions Count",
     count: 0,
   },
   {
     title: "Under Replicated Partitions",
     count: 0,
   },
   {
     title: "Under Min ISR Partitions",
     count: 632,
   }
 ]
 
type TSystemBoard = {
   title: string,
   xaxis?: string,
   yaxis?: string,
   data: Object[]
}

 export const systemBoards: TSystemBoard[] = [
   {
      title: "CPU Usage",
      yaxis: "Cores",
      data: [
         {
            timestamp: "09:00",
            machine1: 80,
            machine2: 60,
         },
         {
            timestamp: "09:15",
            machine1: 70,
            machine2: 50,
         },
         {
            timestamp: "09:30",
            machine1: 50,
            machine2: 40,
         },
         {
            timestamp: "09:45",
            machine1: 60,
            machine2: 55,
         },
         {
            timestamp: "10:00",
            machine1: 75,
            machine2: 65,
         },
         {
            timestamp: "10:15",
            machine1: 85,
            machine2: 70,
         },
         {
            timestamp: "10:30",
            machine1: 90,
            machine2: 75,
         },
         {
            timestamp: "10:45",
            machine1: 80,
            machine2: 60,
         },
         {
            timestamp: "11:00",
            machine1: 70,
            machine2: 50,
         },
         {
            timestamp: "11:15",
            machine1: 60,
            machine2: 45,
         },
         {
            timestamp: "11:30",
            machine1: 55,
            machine2: 40,
         },
         {
            timestamp: "11:45",
            machine1: 50,
            machine2: 35,
         },
         {
            timestamp: "12:00",
            machine1: 40,
            machine2: 30,
         },
         {
            timestamp: "12:15",
            machine1: 45,
            machine2: 35,
         },
         {
            timestamp: "12:30",
            machine1: 55,
            machine2: 40,
         },
         {
            timestamp: "12:45",
            machine1: 65,
            machine2: 50,
         },
         {
            timestamp: "13:00",
            machine1: 70,
            machine2: 55,
         }
      ] 
   },
   {
      title: "JVM Memory Used",
      yaxis: "Memory",
      data: [
         {
            timestamp: "09:00",
            machine1: 700,
            machine2: 800,
          },
          {
            timestamp: "09:15",
            machine1: 750,
            machine2: 850,
          },
          {
            timestamp: "09:30",
            machine1: 600,
            machine2: 700,
          },
          {
            timestamp: "09:45",
            machine1: 650,
            machine2: 750,
          },
          {
            timestamp: "10:00",
            machine1: 800,
            machine2: 900,
          },
          {
            timestamp: "10:15",
            machine1: 750,
            machine2: 850,
          },
          {
            timestamp: "10:30",
            machine1: 700,
            machine2: 800,
          },
          {
            timestamp: "10:45",
            machine1: 650,
            machine2: 750,
          },
          {
            timestamp: "11:00",
            machine1: 600,
            machine2: 700,
          },
          {
            timestamp: "11:15",
            machine1: 650,
            machine2: 750,
          },
          {
            timestamp: "11:30",
            machine1: 700,
            machine2: 800,
          },
          {
            timestamp: "11:45",
            machine1: 750,
            machine2: 850,
          },
          {
            timestamp: "12:00",
            machine1: 800,
            machine2: 900,
          },
          {
            timestamp: "12:15",
            machine1: 750,
            machine2: 850,
          },
          {
            timestamp: "12:30",
            machine1: 700,
            machine2: 800,
          },
          {
            timestamp: "12:45",
            machine1: 650,
            machine2: 750,
          },
          {
            timestamp: "13:00",
            machine1: 600,
            machine2: 700,
          },
      ]
   },
   {
      title: "Time spent in GC",
      yaxis: "% time in GC",
      data: [
         {
            timestamp: "09:00",
            machine1: 2.5,
            machine2: 1.8,
          },
          {
            timestamp: "09:15",
            machine1: 3.2,
            machine2: 2.1,
          },
          {
            timestamp: "09:30",
            machine1: 2.1,
            machine2: 1.5,
          },
          {
            timestamp: "09:45",
            machine1: 1.9,
            machine2: 1.2,
          },
          {
            timestamp: "10:00",
            machine1: 2.8,
            machine2: 1.7,
          },
          {
            timestamp: "10:15",
            machine1: 2.3,
            machine2: 1.6,
          },
          {
            timestamp: "10:30",
            machine1: 1.7,
            machine2: 1.3,
          },
          {
            timestamp: "10:45",
            machine1: 2.1,
            machine2: 1.4,
          },
          {
            timestamp: "11:00",
            machine1: 2.4,
            machine2: 1.8,
          },
          {
            timestamp: "11:15",
            machine1: 2.9,
            machine2: 2.1,
          },
          {
            timestamp: "11:30",
            machine1: 2.2,
            machine2: 1.7,
          },
          {
            timestamp: "11:45",
            machine1: 1.8,
            machine2: 1.4,
          },
          {
            timestamp: "12:00",
            machine1: 2.3,
            machine2: 1.6,
          },
          {
            timestamp: "12:15",
            machine1: 2.6,
            machine2: 1.9,
          },
          {
            timestamp: "12:30",
            machine1: 1.6,
            machine2: 1.2,
          },
          {
            timestamp: "12:45",
            machine1: 1.9,
            machine2: 1.5,
          },
          {
            timestamp: "13:00",
            machine1: 2.1,
            machine2: 1.6,
          },
      ]
   }
];
      

type TConsumerSubscribedTopic = {
   name: string,
   status: string,
   topic: string[],
}

export const consumerSubscribedTopics: TConsumerSubscribedTopic[] = [
   {
      name: "COS001",
      status: "ONLINE",
      topic: ["topic1", "topic2", "topic3"],
    },
    {
      name: "COS002",
      status: "OFFLINE",
      topic: ["topic4", "topic5"],
    },
    {
      name: "COS003",
      status: "ONLINE",
      topic: ["topic2", "topic3"],
    },
    {
      name: "COS004",
      status: "OFFLINE",
      topic: ["topic1", "topic4", "topic5"],
    },
    {
      name: "COS005",
      status: "ONLINE",
      topic: ["topic3"],
    },
    {
      name: "COS006",
      status: "OFFLINE",
      topic: ["topic1", "topic2"],
    },
    {
      name: "COS007",
      status: "ONLINE",
      topic: ["topic4", "topic5"],
    },
    {
      name: "COS008",
      status: "OFFLINE",
      topic: ["topic2", "topic3"],
    },
    {
      name: "COS009",
      status: "ONLINE",
      topic: ["topic1"],
    },
    {
      name: "COS010",
      status: "OFFLINE",
      topic: ["topic3", "topic4", "topic5"],
    },
]

type TRefreshRate = {
   label: string
   value: string,
}

export const refreshRates: TRefreshRate[] = [
   {
      label: "30s",
      value: "30"
   },
   {
      label: "1min",
      value: "60"
   },
   {
      label: "5min",
      value: "300"
   },
]

export const periods = [
   {
      label: "Last 30 minutes",
      value: "30"
   },
   {
      label: "Last 1 hour",
      value: "60"
   },
   {
      label: "Last 6 hours",
      value: "360"
   
   }
]