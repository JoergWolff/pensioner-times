export type MeetingModel = {
    id?: string
    meetingDay: string,
    meetingTime: string,
    description?: string,
    maxUser: string,
    userCounter: string
    userIds: string[],
    placeId: string
}