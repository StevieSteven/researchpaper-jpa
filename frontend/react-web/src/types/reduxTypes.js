

export type Action = {
    type: string,
    payload: any
}

export type DispatchFunction = (action: Action) => {}